#!/bin/bash
# Must be run as ubuntu user

echo 'Working dir:'
pwd
env

# ensure clean workspace
rm -rf *

export QA_TestStartTime=$(date +%Y%m%d_%H%M%S)
export QA_TomcatFolder=${WORKSPACE}/dotcms/dotserver/tomcat-8.0.18
export QA_TomcatLogFile=${QA_TomcatFolder}/logs/catalina.out
export QA_AccessLogFile=${QA_TomcatFolder}/logs/dotcms_access..$(date +%Y-%m-%d).log
export QA_StarterFullFilePath=${QA_TomcatFolder}/webapps/ROOT/starter.zip

export QA_RunLabel=${QA_Milestone}_dotCMSServer_${QA_OS}_${BUILD_NUMBER}_${QA_DB}_${QA_TestStartTime}
export QA_TestArtifactFilename=${QA_RunLabel}_Artifacts.tar.gz

#QA_DBInstance=`echo ${BUILD_TAG} | sed 's/_/-/g'`
tempversion=`echo ${DOTCMS_VERSION} | sed 's/\./-/g'`

QA_DBInstance=DB-${tempversion}-Linux-${BUILD_NUMBER}
export QA_DBInstance
echo "QA_DBInstance=${QA_DBInstance}"

echo "Sending IP Address to ${QA_SERVER_IP_URL}"
ifconfig eth0 | grep "inet addr" | awk -F: '{print $2}' | awk '{print $1}' > ip.txt
aws s3 cp ./ip.txt ${QA_SERVER_IP_URL}

echo "Initializing" > status.txt
aws s3 cp ./status.txt ${QA_SERVER_STATUS_URL}

#echo 'Adding ssh keys'
#aws s3 cp s3://qa.dotcms.com/testautomation/dotcmsqa /home/ubuntu/.ssh/dotcmsqa
#chmod 600 /home/ubuntu/.ssh/dotcmsqa
#aws s3 cp s3://qa.dotcms.com/testautomation/dotcmsqa.pub /home/ubuntu/.ssh/dotcmsqa.pub
#chmod 600 /home/ubuntu/.ssh/dotcmsqa.pub

#echo "Host *">/home/ubuntu/.ssh/config
#echo "    StrictHostKeyChecking no">>/home/ubuntu/.ssh/config

eval $(ssh-agent)
ssh-add /home/ubuntu/.ssh/dotcmsqa

echo 'Cloning qa repo'
cd ${WORKSPACE}
git clone git@github.com:dotCMS/qa.git
echo "Checking out master-${DOTCMS_VERSION} branch"
cd qa
git checkout master-${DOTCMS_VERSION}
cd ${WORKSPACE}

echo 'Pulling down and extracting dotCMS build'
sudo mkdir -p ${WORKSPACE}/downloads
sudo chown -R ubuntu:ubuntu ${WORKSPACE}/downloads
wget -q -O ${WORKSPACE}/downloads/dotcms.targz $DOTCMS_TAR_GZ_URL
sudo mkdir -p ${WORKSPACE}/dotcms
sudo chown -R ubuntu:ubuntu ${WORKSPACE}/dotcms
pushd ${WORKSPACE}/dotcms
tar -xvf ${WORKSPACE}/downloads/dotcms.targz > /dev/null

if [ -z $QA_StarterURL ]
then
	echo 'NOT replacing starter'
else
	echo 'Pulling down and replacing starter'
	aws s3 cp ${QA_StarterURL} ${QA_StarterFullFilePath}
fi

echo 'Setting index pages to legacy setting'
sed -i 's/CMS_INDEX_PAGE = index/CMS_INDEX_PAGE = index.html/g' ${QA_TomcatFolder}/webapps/ROOT/WEB-INF/classes/dotmarketing-config.properties

echo 'Creating and configuring DB'
pushd ${WORKSPACE}/qa
if [ ${QA_DB} = "MSSQL_RDS" ] || [ ${QA_DB} = "MySQL_RDS" ] || [ ${QA_DB} = "Oracle_RDS" ] || [ ${QA_DB} = "PostgreSQL_RDS" ]
then
	ant -DDBInstanceID=${QA_DBInstance} start-aws-db-server
	sleep 60
	dbstatus=`aws rds describe-db-instances --db-instance-identifier ${QA_DBInstance} | python -c 'import sys, json; print json.load(sys.stdin)["DBInstances"][0]["DBInstanceStatus"]'`
	echo "dbstatus=${dbstatus}"
	while [ $dbstatus != "available" ]
	do
		echo "waiting for DB Server to become available..."
		sleep 30
		dbstatus=`aws rds describe-db-instances --db-instance-identifier ${QA_DBInstance} | python -c 'import sys, json; print json.load(sys.stdin)["DBInstances"][0]["DBInstanceStatus"]'`
	done
	echo "dbstatus=$dbstatus"
	dbserver=`aws rds describe-db-instances --db-instance-identifier ${QA_DBInstance} | python -c 'import sys, json; print json.load(sys.stdin)["DBInstances"][0]["Endpoint"]["Address"]'`
	export dbserver
	echo "dbserver=${dbserver}"
fi
ant create-db
ant create-context-xml
popd

echo 'Starting dotCMS'
echo "Starting dotCMS" > status.txt
aws s3 cp ./status.txt ${QA_SERVER_STATUS_URL}

bin/startup.sh
sleep 30
logcount=`grep -c "org.apache.catalina.startup.Catalina.start Server startup in" ${QA_TomcatLogFile}`
echo "logcount=${logcount}"
while [ $logcount -lt 1 ]
do
	echo "sleeping..."
	sleep 10
	logcount=`grep -c "org.apache.catalina.startup.Catalina.start Server startup in" ${QA_TomcatLogFile}`
done
echo "logcount=$logcount"


echo 'Building and deploying qa_automation plugin'
cd ${WORKSPACE}/qa/plugins/com.dotcms.rest.qa_automation
./gradlew jar
cp ./build/libs/com.dotcms.rest.qa_automation-0.1.jar ${QA_TomcatFolder}/webapps/ROOT/WEB-INF/felix/load/.

echo 'Getting trial license'
cp ${WORKSPACE}/qa/artifacts/license/trial.jsp ${QA_TomcatFolder}/webapps/ROOT/trial.jsp
curl http://localhost:8080/trial.jsp

if [ ${QA_OPTION_AUTHORING_SERVER} = "true" ]
then
	echo 'YES, I am an authoring server - must wait for receiving server to come online...'

	aws s3 cp ${QA_SERVER_RECEIVING_IP_URL} ./ip_receiving.txt
	while [ ! -f ./ip_receiving.txt ]
	do
		echo "waiting for QA_SERVER_RECEIVING_IP_URL file ..."
		sleep 30
		aws s3 cp ${QA_SERVER_RECEIVING_IP_URL} ./ip_receiving.txt
	done
	export DOTCMS_SERVER_RECEIVING_IP=$(cat ./ip_receiving.txt)
	echo "DOTCMS_SERVER_RECEIVING_IP = ${DOTCMS_SERVER_RECEIVING_IP}"

	aws s3 cp ${QA_SERVER_RECEIVING_STATUS_URL} ./status_receiving.txt
	while [ ! -f ./status_receiving.txt ]
	do
	    echo "waiting for receiving server status file..."
	    sleep 30
	    aws s3 cp ${QA_SERVER_RECEIVING_STATUS_URL} ./status_receiving.txt
	done

	running=`grep -c "Running" ./status_receiving.txt`
	echo "running=${running}"
	while [ $running -lt 1 ]
	do
	    echo "INFO - waiting for receiving server to be in Running state...."
	    sleep 60
	    aws s3 cp ${QA_SERVER_RECEIVING_STATUS_URL} ./status_receiving.txt
	    running=`grep -c "Running" ./status_receiving.txt`
	done
	echo "running=$running"

else
	echo 'NOT an authoring server - continuing on'
fi
echo "Running" > status.txt
aws s3 cp ./status.txt ${QA_SERVER_STATUS_URL}

date
# polling looking for request to shutdown dotCMS server - until then keep the server running
logcount2=`grep -c "shutdown.jsp" ${QA_AccessLogFile}`
echo "logcount2=${logcount2}"
while [ $logcount2 -lt 1 ]
do
	echo "running..."
	sleep 60
	logcount2=`grep -c "shutdown.jsp" ${QA_AccessLogFile}`
done
echo "logcount2=$logcount2"
date

echo "Shutting down dotCMS" > status.txt
aws s3 cp ./status.txt ${QA_SERVER_STATUS_URL}

echo 'Shutting down dotCMS'
${WORKSPACE}/dotcms/bin/shutdown.sh
popd


pushd ${WORKSPACE}/qa
ant drop-db
if [ ${QA_DB} = "MSSQL_RDS" ] || [ ${QA_DB} = "MySQL_RDS" ] || [ ${QA_DB} = "Oracle_RDS" ] || [ ${QA_DB} = "PostgreSQL_RDS" ]
then
	echo 'Shutting down RDS instance'
	ant -DDBInstanceID=${QA_DBInstance} shutdown-aws-db-server
fi
popd 


echo 'Grabbing and packaging logs'
sleep 10
mkdir ${WORKSPACE}/temp_log
pushd ${WORKSPACE}/temp_log
mkdir ${WORKSPACE}/temp_log/dotcms
cp -a ${QA_TomcatFolder}/logs/ ./dotcms
tar -cvzf ../${QA_TestArtifactFilename} .
popd
rm -rf ${WORKSPACE}/temp_log/

echo 'Storing logs into s3'
aws s3 cp ${WORKSPACE}/${QA_TestArtifactFilename} s3://qa.dotcms.com/testartifacts/${QA_TestArtifactFilename}
 
echo 'Cleaning up - preparing for another possible run'
rm -rf ${WORKSPACE}/downloads
#rm -rf ${WORKSPACE}/*
aws s3 rm ${QA_SERVER_IP_URL}
aws s3 rm ${QA_SERVER_STATUS_URL}

#!/bin/bash
# Must be run as ubuntu user

echo 'Working dir:'
pwd
env

# ensure clean workspace
rm -rf *

export QA_TestStartTime=$(date +%Y%m%d_%H%M%S)
export QA_StarterURL=s3://qa.dotcms.com/starters/3.1_qastarter_v.0.4b_c0ae3facdd.zip
export QA_TomcatFolder=${WORKSPACE}/dotcms/dotserver/tomcat-7.0.54
export QA_TomcatLogFile=${QA_TomcatFolder}/logs/catalina.out
export QA_AccessLogFile=${QA_TomcatFolder}/logs/dotcms_access.$(date +%Y-%m-%d).log
export QA_StarterFullFilePath=${QA_TomcatFolder}/webapps/ROOT/starter.zip

export QA_DB=H2
export QA_Browser=FIREFOX
export QA_Country=US
export QA_Language=en
export QA_OS=Ubuntu
export QA_Milestone=${DOTCMS_VERSION}
export QA_RunLabel=${QA_Milestone}_JenkinsSeleniumTester_${BUILD_NUMBER}_${QA_OS}_${QA_DB}_${QA_Browser}_${QA_Language}_${QA_Country}_${QA_TestStartTime}
export QA_TestArtifactFilename=${QA_RunLabel}_Artifacts.tar.gz


echo 'Creating AWS credentials'
mkdir /home/ubuntu/.aws
echo '[default]' > /home/ubuntu/.aws/config
echo 'region = us-east-1' >> /home/ubuntu/.aws/config
echo 'aws_access_key_id = AKIAJB7GBDVDNTROV7LQ' >> /home/ubuntu/.aws/config
echo 'aws_secret_access_key = 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' >> /home/ubuntu/.aws/config

echo "Sending IP Address to ${QA_SERVER_IP_URL}"
ifconfig eth0 | grep "inet addr" | awk -F: '{print $2}' | awk '{print $1}' > ip.txt
aws s3 cp ./ip.txt ${QA_SERVER_IP_URL}

echo "Initializing" > status.txt
aws s3 cp ./status.txt ${QA_SERVER_STATUS_URL}

echo 'Pulling down and extracting dotCMS build'
mkdir ${WORKSPACE}/downloads
wget -q -O ./downloads/dotcms.targz $DOTCMS_TAR_GZ_URL
sudo mkdir -p ${WORKSPACE}/dotcms
sudo chown -R ubuntu:ubuntu ${WORKSPACE}/dotcms
pushd ${WORKSPACE}/dotcms
tar -xvf ${WORKSPACE}/downloads/dotcms.targz > /dev/null

echo 'Pulling down and replacing starter'
aws s3 cp ${QA_StarterURL} ${QA_StarterFullFilePath}

echo 'Setting index pages to legacy setting'
sed -i 's/CMS_INDEX_PAGE = index/CMS_INDEX_PAGE = index.html/g' ${QA_TomcatFolder}/webapps/ROOT/WEB-INF/classes/dotmarketing-config.properties

echo 'Starting dotCMS'
echo "Starting dotCMS" > status.txt
aws s3 cp ./status.txt ${QA_SERVER_STATUS_URL}

bin/startup.sh
sleep 30
logcount=`grep -c "org.apache.catalina.startup.Catalina start" ${QA_TomcatLogFile}`
echo "logcount=${logcount}"
while [ $logcount -lt 1 ]
do
	echo "sleeping..."
	sleep 10
	logcount=`grep -c "org.apache.catalina.startup.Catalina start" ${QA_TomcatLogFile}`
done
echo "logcount=$logcount"
popd

echo 'Adding ssh keys'
aws s3 cp s3://qa.dotcms.com/testautomation/dotcmsqa /home/ubuntu/.ssh/dotcmsqa
chmod 600 /home/ubuntu/.ssh/dotcmsqa
aws s3 cp s3://qa.dotcms.com/testautomation/dotcmsqa.pub /home/ubuntu/.ssh/dotcmsqa.pub
chmod 600 /home/ubuntu/.ssh/dotcmsqa.pub

echo "Host *">/home/ubuntu/.ssh/config
echo "    StrictHostKeyChecking no">>/home/ubuntu/.ssh/config

eval $(ssh-agent)
ssh-add /home/ubuntu/.ssh/dotcmsqa

echo 'Cloning qa repo'
cd ${WORKSPACE}
git clone git@github.com:dotCMS/qa.git
echo "Checking out master-${DOTCMS_VERSION} branch"
cd qa
git checkout master-${DOTCMS_VERSION}

echo 'Building and deploying qa_automation plugin'
cd ${WORKSPACE}/qa/plugins/com.dotcms.rest.qa_automation
./gradlew jar
cp ./build/libs/com.dotcms.rest.qa_automation-0.1.jar ${QA_TomcatFolder}/webapps/ROOT/WEB-INF/felix/load/.

echo 'Getting trial license'
cp ${WORKSPACE}/qa/artifacts/license/trial.jsp ${QA_TomcatFolder}/webapps/ROOT/trial.jsp
curl http://localhost:8080/trial.jsp

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
rm -rf /home/ubuntu/.aws

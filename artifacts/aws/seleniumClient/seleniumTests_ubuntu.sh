#!/bin/bash
# Must be run as ubuntu user

echo 'Working dir:'
pwd
env

# ensure clean workspace
rm -rf *

export QA_TestStartTime=$(date +%Y%m%d_%H%M%S)

export QA_RunLabel=${DOTCMS_VERSION}_${QA_OS}_${BUILD_NUMBER}_${QA_DB}_${QA_Browser}_${QA_Language}_${QA_Country}_${QA_TestStartTime}
export QA_TestArtifactFilename=${QA_RunLabel}_Artifacts.tar.gz

if [ -z "$QA_TestSuite" ]
then
    export QA_TestSuite="testng.xml"
fi

echo 'Exporting display to use Xvfb service'
export DISPLAY=:99


aws s3 cp ${QA_SERVER_IP_URL} ./ip.txt
while [ ! -f ./ip.txt ]
do
	echo "waiting for DOTCMS_SERVER_IP file"
	sleep 30
	aws s3 cp ${QA_SERVER_IP_URL} ./ip.txt
done
export DOTCMS_SERVER_IP=$(cat ./ip.txt)
echo "DOTCMS_SERVER_IP = ${DOTCMS_SERVER_IP}"

etchostname=`cat /etc/hostname`
grep $DOTCMS_SERVER_IP /etc/hosts | grep -c "$etchostname"
echo '----------------------------------------'
cat /etc/hosts
if [ `grep $DOTCMS_SERVER_IP /etc/hosts | grep -c "$etchostname"` -eq 0 ]
then
    echo "Adding hostnames to ${DOTCMS_SERVER_IP} in /etc/hosts"
    sudo cp -a /etc/hosts /etc/hosts.backup
    sudo cp -a /etc/hosts ./hosts
    sudo chown ubuntu:ubuntu ./hosts 
    sudo echo "${DOTCMS_SERVER_IP}    ${etchostname} qademo.dotcms.com m.qademo.dotcms.com qashared.dotcms.com qahost01.dotcms.com qahost02.dotcms.com qahost03.dotcms.com qahost04.dotcms.com qahost05.dotcms.com qahost06.dotcms.com qahost07.dotcms.com\r\n" >> ./hosts
    sudo chown root:root ./hosts
    sudo cp -a ./hosts /etc/hosts
fi

hostname=`hostname`
if [ "$hostname" != "$etchostname" ]
then
    echo "Setting hostname to $etchostname"
    sudo hostname "$etchostname"
fi
echo '****************************************'
cat /etc/hosts

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
git clone git@github.com:dotCMS/qa.git
echo "Checking out ${QA_BRANCH} branch"
cd qa
git checkout ${QA_BRANCH}
cd ..

echo '********** END OF PART 1 **********'

cd ./qa

echo 'Building testng/selenium tests'
./gradlew installDist
cd ..

aws s3 cp ${QA_SERVER_STATUS_URL} ./status.txt
while [ ! -f ./status.txt ]
do
    echo "waiting for server status file..."
    sleep 30
    aws s3 cp ${QA_SERVER_STATUS_URL} ./status.txt
done

running=`grep -c "Running" ./status.txt`
echo "running=${running}"
while [ $running -lt 1 ]
do
    echo "INFO - waiting for dotCMS server to be in Running state...."
    sleep 60
    aws s3 cp ${QA_SERVER_STATUS_URL} ./status.txt
    running=`grep -c "Running" ./status.txt`
done
echo "running=$running"

# Is this a push / publish situation?
# now server is in running state, need to check if there is a receiving server and if so, what its IP address is
# the main server is the authoring server and it will wait for the receiving server to be running before it reports its status as running
# so, if there is an ip file for the receiving server, it exists and is running - otherwise not a push / publish situation
aws s3 cp ${QA_SERVER_RECEIVING_IP_URL} ./ip_receiving.txt
if [ -f ./ip_receiving.txt ]
then
    PUSH_PUB_STATUS='true'
    QA_SERVER_RECEIVING_IP=`cat ./ip_receiving.txt`
else
    PUSH_PUB_STATUS='false'
fi
echo "PUSH_PUB_STATUS=${PUSH_PUB_STATUS}"
echo "QA_SERVER_RECEIVING_IP=${QA_SERVER_RECEIVING_IP}"

export EXIT_CODE=255
cd ${WORKSPACE}/qa/build/install/qa
echo "Running testng/selenium tests - pwd = $(pwd)"
export JAVA_OPTS="-DreportResultsInTestrail=true -Dtestrail.Milestone=${DOTCMS_VERSION} -Dtestrail.RunLabel=${QA_RunLabel} -DbrowserToTarget=${QA_Browser} -Duser.language=${QA_Language} -Duser.country=${QA_Country}"
if [ "${PUSH_PUB_STATUS}" == "true" ]
then
    export JAVA_OPTS="${JAVA_OPTS} -Dpushpublising.autoring.server=${DOTCMS_SERVER_IP} -Dpushpublising.autoring.server.port=8080 -Dpushpublising.receiver.server=${QA_SERVER_RECEIVING_IP} -Dpushpublising.receiver.server.port=8080"
fi
echo "JAVA_OPTS=${JAVA_OPTS}"
bin/qa  -testjar lib/qa-0.1.jar -xmlpathinjar ${QA_TestSuite} -listener com.dotcms.qa.testng.listeners.TestRunCreator.class,com.dotcms.qa.testng.listeners.TestResultReporter.class -d "${WORKSPACE}/testngresults_${QA_Database}_${QA_Browser}_${QA_Language}_${QA_Country}"
EXIT_CODE=$?
echo "EXIT_CODE=${EXIT_CODE}"
cd ${WORKSPACE}

echo 'Grabbing and packaging logs'
sleep 10
mkdir temp_log
pushd temp_log
cp -a ${WORKSPACE}/qa/build/install/qa/ .
cp -a ${WORKSPACE}/testngresults_${QA_Database}_${QA_Browser}_${QA_Language}_${QA_Country} .
tar -cvzf ../${QA_TestArtifactFilename} .
popd
rm -rf temp_log/

echo 'Storing logs into s3'
aws s3 cp ${QA_TestArtifactFilename} s3://qa.dotcms.com/testartifacts/${QA_TestArtifactFilename}

echo 'Copying testng results to workspace'
cp -a "testngresults_${QA_Database}_${QA_Browser}_${QA_Language}_${QA_Country}" "${WORKSPACE}"

echo 'Cleaning up - preparing for another possible run'
sudo cp -a /etc/hosts.backup /etc/hosts
rm ${QA_TestArtifactFilename}

cd ${WORKSPACE}
echo "DOTCMS_VERSION=${DOTCMS_VERSION}" > ./params
echo "QA_BRANCH=${QA_BRANCH}" >> ./params
echo "DOTCMS_SERVER_IP=${DOTCMS_SERVER_IP}" >> ./params

echo "EXIT_CODE=${EXIT_CODE}"
exit ${EXIT_CODE}
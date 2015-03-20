#!/bin/bash
# Must be run as ubuntu user
EXIT_CODE=255

echo 'Working dir:'
pwd
env

# ensure clean workspace
rm -rf *

export QA_TestStartTime=$(date +%Y%m%d_%H%M%S)

export QA_DB=H2
export QA_Browser=FIREFOX
export QA_Country=US
export QA_Language=en
export QA_OS=Ubuntu
export QA_Milestone=${DOTCMS_VERSION}
export QA_RunLabel=${QA_Milestone}_JenkinsSeleniumTester_${BUILD_NUMBER}_${QA_OS}_${QA_DB}_${QA_Browser}_${QA_Language}_${QA_Country}_${QA_TestStartTime}
export QA_TestArtifactFilename=${QA_RunLabel}_Artifacts.tar.gz

echo 'Exporting display to use Xvfb service'
export DISPLAY=:99

echo 'Creating AWS credentials'
mkdir /home/ubuntu/.aws
echo '[default]' > /home/ubuntu/.aws/config
echo 'region = us-east-1' >> /home/ubuntu/.aws/config
echo 'aws_access_key_id = AKIAJB7GBDVDNTROV7LQ' >> /home/ubuntu/.aws/config
echo 'aws_secret_access_key = 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' >> /home/ubuntu/.aws/config

aws s3 cp ${QA_SERVER_IP_URL} ./ip.txt
while [ ! -f ./ip.txt ]
do
	echo "waiting for DOTCMS_SERVER_IP file"
	sleep 30
	aws s3 cp ${QA_SERVER_IP_URL} ./ip.txt
done
DOTCMS_SERVER_IP=$(cat ./ip.txt)
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
git clone git@github.com:dotCMS/qa.git
echo "Checking out master-${DOTCMS_VERSION} branch"
pushd qa
git checkout master-${DOTCMS_VERSION}

echo 'Building testng/selenium tests'
./gradlew installapp &
wait
popd

echo 'Running testng/selenium tests'
pushd qa/build/install/qa
export JAVA_OPTS="-Dtestrail.Milestone=${QA_Milestone} -Dtestrail.RunLabel=${QA_RunLabel} -DbrowserToTarget=${QA_Browser} -Duser.language=${QA_Language} -Duser.country=${QA_Country}"
bin/qa  -testjar lib/qa-0.1.jar -listener com.dotcms.qa.testng.listeners.TestRunCreator.class,com.dotcms.qa.testng.listeners.TestResultReporter.class -d "${WORKSPACE}/testngresults_${QA_Database}_${QA_Browser}_${QA_Language}_${QA_Country}"
EXIT_CODE=$?
echo "EXIT_CODE=${EXIT_CODE}"
#wait
popd

echo 'Grabbing and packaging logs'
sleep 10
mkdir temp_log
pushd temp_log
cp -a ${WORKSPACE}/qa/build/install/qa/ .
tar -cvzf ../${QA_TestArtifactFilename} .
popd
rm -rf temp_log/

echo 'Storing logs into s3'
aws s3 cp ${QA_TestArtifactFilename} s3://qa.dotcms.com/testartifacts/${QA_TestArtifactFilename}

echo 'Copying testng results to workspace'
cp -a "testngresults_${QA_Database}_${QA_Browser}_${QA_Language}_${QA_Country}" "${WORKSPACE}"

echo 'Shutting down dotCMS server'
curl -I http://${DOTCMS_SERVER_IP}:8080/shutdown.jsp

echo 'Cleaning up - preparing for another possible run'
sudo cp -a /etc/hosts.backup /etc/hosts
rm -rf /home/ubuntu/.aws
rm ${QA_TestArtifactFilename}

echo "EXIT_CODE=${EXIT_CODE}"
exit ${EXIT_CODE}

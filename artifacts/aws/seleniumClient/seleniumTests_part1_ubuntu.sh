#!/bin/bash
# Must be run as ubuntu user

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
cd qa
git checkout master-${DOTCMS_VERSION}

echo '********** END OF PART 1 **********'

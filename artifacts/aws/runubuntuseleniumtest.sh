#!/bin/bash
# Must be run as ubuntu user

cd /home/ubuntu
env > /home/ubuntu/test_env.txt

# export display to use Xvfb service
export DISPLAY=:99

# create aws credentials
mkdir /home/ubuntu/.aws
echo '[default]' > /home/ubuntu/.aws/config
echo 'region = us-east-1' >> /home/ubuntu/.aws/config
echo 'aws_access_key_id = AKIAJB7GBDVDNTROV7LQ' >> /home/ubuntu/.aws/config
echo 'aws_secret_access_key = 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' >> /home/ubuntu/.aws/config

# pull down and start dotCMS build
mkdir /home/ubuntu/downloads
wget -q -O ./downloads/dotcms.targz $TAR_URL
sudo mkdir -p /opt/dotcms
sudo chown -R ubuntu:ubuntu /opt/dotcms
cd /opt/dotcms
tar -xvf /home/ubuntu/downloads/dotcms.targz
bin/startup.sh

# add ssh keys
aws s3 cp s3://qa.dotcms.com/testautomation/dotcmsqa /home/ubuntu/.ssh/dotcmsqa
chmod 600 /home/ubuntu/.ssh/dotcmsqa
aws s3 cp s3://qa.dotcms.com/testautomation/dotcmsqa.pub /home/ubuntu/.ssh/dotcmsqa.pub
chmod 600 /home/ubuntu/.ssh/dotcmsqa.pub
eval $(ssh-agent)
ssh-add /home/ubuntu/.ssh/dotcmsqa
ssh -Tq -o StrictHostKeyChecking=no git@github.com

# clone qa repo
git clone git@github.com:dotCMS/qa.git

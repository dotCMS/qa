#!/bin/bash
# Must be run as root user

date > /root/setup_start.txt
env > /root/setup_env.txt

#setup jenkins directory
mkdir /opt/jenkins
chown ubuntu:ubuntu /opt/jenkins

#update software repo lists
apt-get -y update
#upgrade all installed software
apt-get -y upgrade

# update hard and soft file limits
sed -i.dist 's/\(.*End of file.*\)/\n \
\*\thard\tnofile\t1000000 \
\*\tsoft\tnofile\t1000000 \
root\thard\tnofile\t1000000 \
root\tsoft\tnofile\t1000000 \
\n\1/g' /etc/security/limits.conf
echo -e "session\trequired\tpam_limits.so" >> /etc/pam.d/common-session
echo -e "session\trequired\tpam_limits.so" >> /etc/pam.d/common-session-noninteractive

#install expected packages
apt-get -y install telnet uuid xfsprogs git awscli xvfb firefox unzip
#apt-get -y install bsd-mailx postfix
#chkconfig postfix on

# Set timezone to central
timedatectl set-timezone America/Chicago

# create download folder
mkdir /root/downloads

# install ant
#cd /root/downloads
#wget http://apache.osuosl.org/ant/binaries/apache-ant-1.9.4-bin.tar.gz
#mkdir -p /opt/apache/ant
#cd /opt/apache/ant
#tar -xvf /root/downloads/apache-ant-1.9.4-bin.tar.gz
#ln -s apache-ant-1.9.4/ latest

# create aws credentials
mkdir /root/.aws
echo '[default]' > /root/.aws/config
echo 'region = us-east-1' >> /root/.aws/config
echo 'aws_access_key_id = AKIAJB7GBDVDNTROV7LQ' >> /root/.aws/config
echo 'aws_secret_access_key = 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' >> /root/.aws/config

# download and install java
cd /root/downloads
aws s3 cp s3://qa.dotcms.com/testautomation/software/java/jdk-7u71-linux-x64.gz .
mkdir -p /opt/oracle/java
cd /opt/oracle/java/
tar -xvf /root/downloads/jdk-7u71-linux-x64.gz
ln -s jdk1.7.0_*/ latest
update-alternatives --install /usr/bin/java java /opt/oracle/java/latest/bin/java 99999
update-alternatives --install /usr/bin/javac javac /opt/oracle/java/latest/bin/javac 99999

# pull down and start dotCMS build
# cd /root/downloads
# aws s3 cp s3://qa.dotcms.com/testautomation/pullnightlybuild.sh .
# chmod u+x ./pullnightlybuild.sh
# ./pullnightlybuild.sh
# mkdir -p /opt/dotcms
# cd /opt/dotcms
# tar -xvf /root/downloads/dotcms_*.targz
# chown -R ubuntu:ubuntu /opt/dotcms
#cd /opt/dotcms/
#bin/startup.sh

# add ssh keys
#aws s3 cp s3://qa.dotcms.com/testautomation/dotcmsqa /root/.ssh/dotcmsqa
#chmod 600 /root/.ssh/dotcmsqa
#aws s3 cp s3://qa.dotcms.com/testautomation/dotcmsqa.pub /root/.ssh/dotcmsqa.pub
#chmod 600 /root/.ssh/dotcmsqa.pub
#cd /root
#eval $(ssh-agent)
#ssh-add /root/.ssh/dotcmsqa
#ssh -Tq -o StrictHostKeyChecking=no git@github.com

#git clone git@github.com:dotCMS/qa.git
#mv /root/qa/ /home/ubuntu/qa/
#chown -R ubuntu:ubuntu /home/ubuntu/qa

# copy ssh keys
#sudo cp /root/.ssh/dotcmsqa /home/ubuntu/.ssh/.
#sudo cp /root/.ssh/dotcmsqa.pub /home/ubuntu/.ssh/.
#sudo chown ubuntu:ubuntu /home/ubuntu/.ssh/dotcmsqa*

# configure and start Xvfb service
aws s3 cp s3://qa.dotcms.com/testautomation/xvfb /etc/init.d/xvfb
chmod +x /etc/init.d/xvfb
sudo update-rc.d xvfb defaults
/etc/init.d/xvfb start

date > /root/setup_done.txt
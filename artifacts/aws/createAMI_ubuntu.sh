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
apt-get -y install postgresql-9.3 ant
#mysql-server-5.6

# Set timezone to central
timedatectl set-timezone America/Chicago

# create download folder
mkdir /root/downloads

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

# configure postgreSQL
cd /root/downloads
curl -u b.rent.griffin@dotcms.com:@s3cur3 https://raw.githubusercontent.com/dotCMS/qa/master-${DOTCMS_VERSION}/artifacts/aws/database/postgres/postgresql.conf > postgresql.conf
cp ./postgresql.conf /etc/postgresql/9.3/main/postgresql.conf
curl -u b.rent.griffin@dotcms.com:@s3cur3 https://raw.githubusercontent.com/dotCMS/qa/master-${DOTCMS_VERSION}/artifacts/aws/database/postgres/pg_hba.conf > pg_hba.conf
cp ./pg_hba.conf /etc/postgresql/9.3/main/pg_hba.conf
# restart postgresql server so configuration changes can take effect
/etc/init.d/postgresql restart

# configure mySQL
# TODO

# configure and start Xvfb service
aws s3 cp s3://qa.dotcms.com/testautomation/xvfb /etc/init.d/xvfb
chmod +x /etc/init.d/xvfb
sudo update-rc.d xvfb defaults
/etc/init.d/xvfb start

date > /root/setup_done.txt
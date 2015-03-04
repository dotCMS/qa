#!/bin/bash
# Must be run as ubuntu user

EXIT_CODE=255

cd /home/ubuntu
env

export QA_TestStartTime=$(date +%Y%m%d_%H%M%S)
export QA_StarterURL=s3://qa.dotcms.com/starters/3.1_qastarter_v.0.4b_c0ae3facdd.zip
export QA_TomcatFolder=/opt/dotcms/dotserver/tomcat-7.0.54
export QA_TomcatLogFile=${QA_TomcatFolder}/logs/catalina.out
export QA_StarterFullFilePath=${QA_TomcatFolder}/webapps/ROOT/starter.zip
export QA_TAR_GZ_URL=$TAR_GZ_URL
#export QA_TAR_GZ_URL=http://dotcms.com/contentAsset/raw-data/d1c6451b-8253-4cc9-bda6-1653077b0ef6/targz/dotcms-2015-01-20_10-15.tar.gz

export QA_DB=H2
export QA_Browser=FIREFOX
export QA_Country=US
export QA_Language=en
export QA_OS=Ubuntu
export QA_Milestone=3.1
export QA_RunLabel=${QA_Milestone}_JenkinsSeleniumTester_${BUILD_NUMBER}_${QA_OS}_${QA_DB}_${QA_Browser}_${QA_Language}_${QA_Country}_${QA_TestStartTime}
export QA_TestArtifactFilename=${QA_RunLabel}_Artifacts.tar.gz

# export display to use Xvfb service
export DISPLAY=:99

# create aws credentials
mkdir /home/ubuntu/.aws
echo '[default]' > /home/ubuntu/.aws/config
echo 'region = us-east-1' >> /home/ubuntu/.aws/config
echo 'aws_access_key_id = AKIAJB7GBDVDNTROV7LQ' >> /home/ubuntu/.aws/config
echo 'aws_secret_access_key = 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' >> /home/ubuntu/.aws/config

# pull down and extract dotCMS build
mkdir /home/ubuntu/downloads
wget -q -O ./downloads/dotcms.targz $QA_TAR_GZ_URL
sudo mkdir -p /opt/dotcms
sudo chown -R ubuntu:ubuntu /opt/dotcms
cd /opt/dotcms
tar -xvf /home/ubuntu/downloads/dotcms.targz

# pull down and replace starter
aws s3 cp ${QA_StarterURL} ${QA_StarterFullFilePath}
#aws s3 cp s3://qa.dotcms.com/starters/3.0_qastarter_v.0.4b_release.zip ./dotserver/tomcat-7.0.54/webapps/ROOT/starter.zip

# set index pages to legacy setting
sed -i 's/CMS_INDEX_PAGE = index/CMS_INDEX_PAGE = index.html/g' ${QA_TomcatFolder}/webapps/ROOT/WEB-INF/classes/dotmarketing-config.properties

# start dotCMS
bin/startup.sh
echo $?
sleep 30
logcount=`grep -c "org.apache.catalina.startup.Catalina start" ${QA_TomcatLogFile}`
echo $?
echo "logcount=${logcount}"
while [ $logcount -lt 1 ]
do
	echo "sleeping..."
	sleep 10
	logcount=`grep -c "org.apache.catalina.startup.Catalina start" ${QA_TomcatLogFile}`
done
echo "logcount=$logcount"

# add ssh keys
aws s3 cp s3://qa.dotcms.com/testautomation/dotcmsqa /home/ubuntu/.ssh/dotcmsqa
chmod 600 /home/ubuntu/.ssh/dotcmsqa
aws s3 cp s3://qa.dotcms.com/testautomation/dotcmsqa.pub /home/ubuntu/.ssh/dotcmsqa.pub
chmod 600 /home/ubuntu/.ssh/dotcmsqa.pub

echo "Host *">/home/ubuntu/.ssh/config
echo "    StrictHostKeyChecking no">>/home/ubuntu/.ssh/config

eval $(ssh-agent)
ssh-add /home/ubuntu/.ssh/dotcmsqa
#ssh -Tq -o StrictHostKeyChecking=no git@github.com

# clone qa repo
cd /home/ubuntu
git clone git@github.com:dotCMS/qa.git
cd qa
git checkout master-${DOTCMS_VERSION}

# build and deploy qa plugin
cd /home/ubuntu/qa/plugins/com.dotcms.rest.qa_automation
./gradlew jar
cp ./build/lib/com.dotcms.rest.qa_automation-0.1.jar ${QA_TomcatFolder}/webapps/ROOT/WEB-INF/felix/load/.

# build selenium test
cd /home/ubuntu/qa
./gradlew installapp

#	Run testng/selenium tests
pushd /home/ubuntu/qa/build/install/qa
export JAVA_OPTS="-Dtestrail.Milestone=${QA_Milestone} -Dtestrail.RunLabel=${QA_RunLabel} -DbrowserToTarget=${QA_Browser} -Duser.language=${QA_Language} -Duser.country=${QA_Country}"
bin/qa  -testjar lib/qa-0.1.jar -listener com.dotcms.qa.testng.listeners.TestRunCreator.class,com.dotcms.qa.testng.listeners.TestResultReporter.class -d "/home/ubuntu/testngresults_${QA_Database}_${QA_Browser}_${QA_Language}_${QA_Country}"
EXIT_CODE=$?
echo "EXIT_CODE=${EXIT_CODE}"
#mv ./qa.log "${resultsdirectory}/testngresults_${database}_${browser}_${language}_${country}/qa.log"
#mv ./*.png "${resultsdirectory}/testngresults_${database}_${browser}_${language}_${country}/."
wait
popd

#	Shutdown dotCMS
/opt/dotcms/bin/shutdown.sh

# grab and package logs
sleep 10
mkdir /home/ubuntu/temp_log
pushd /home/ubuntu/temp_log
cp -a /home/ubuntu/qa/build/install/qa/ .
mkdir /home/ubuntu/temp_log/dotcms
cp -a ${QA_TomcatFolder}/logs/ ./dotcms
tar -cvzf ../${QA_TestArtifactFilename} .
popd
rm -rf /home/ubuntu/temp_log/

# store logs into s3
aws s3 cp /home/ubuntu/${QA_TestArtifactFilename} s3://qa.dotcms.com/testartifacts/${QA_TestArtifactFilename}

# copy testng results to workspace
cp -a "/home/ubuntu/testngresults_${QA_Database}_${QA_Browser}_${QA_Language}_${QA_Country}" "${WORKSPACE}"
 
# cleanup - prepare for another possible run
sudo rm -rf /opt/dotcms 
rm -rf /home/ubuntu/.aws
rm -rf /home/ubuntu/downloads
rm /home/ubuntu/${QA_TestArtifactFilename}

echo "EXIT_CODE=${EXIT_CODE}"
exit ${EXIT_CODE}

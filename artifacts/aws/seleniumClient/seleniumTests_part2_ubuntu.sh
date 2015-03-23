#!/bin/bash
# Must be run as ubuntu user
export EXIT_CODE=255

echo "Running testng/selenium tests - pwd = $(pwd)"
pushd ${WORKSPACE}/qa/build/install/qa
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
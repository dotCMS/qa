#!/bin/bash
# Must be run as ubuntu user

echo 'Cloning qa repo'
git clone git@github.com:dotCMS/qa.git
echo "Checking out master-${DOTCMS_VERSION} branch"
pushd qa
git checkout master-${DOTCMS_VERSION}

echo 'Building testng/selenium tests'
./gradlew installapp
popd
echo '********** END OF PART 1b **********'

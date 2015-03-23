#!/bin/bash
# Must be run as ubuntu user

pwd
cd ./qa

echo 'Building testng/selenium tests'
gradlew installApp
echo '********** END OF PART 1b **********'

#!/bin/bash
# Must be run as ubuntu user

pwd
cd ./qa

echo 'Building testng/selenium tests'
./gradlew installDist
echo '********** END OF PART 1b **********'

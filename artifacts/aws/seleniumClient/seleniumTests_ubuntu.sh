#!/bin/bash
# Must be run as ubuntu user

pwd

echo 'Building testng/selenium tests'
./qa/gradlew installapp
echo '********** END OF PART 1b **********'

#!/bin/bash
# Must be run as ubuntu user

pwd

echo 'Building testng/selenium tests'
./qa/gradlew installApp
echo '********** END OF PART 1b **********'

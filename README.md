qa
==
Private repo for Quality Assurance artifacts.

Initial artifacts will be Java code utilizing Selenium web driver.

Eventually will include configuration management scripting and automated testing scripts.


To build and use Selenium Web Driver Test Suite:
------------------------------------------------
Make sure your host file has entries for the following:
	qademo.dotcms.com
	m.qademo.dotcms.com
	qashared.dotcms.com

Verify that the properties in src/main/resources/POC.properties are correct.  Especially focus on the server port numbers.

Execute the following from within the repo root directory:<br/>
./gradlew           - this will pull down gradle locally<br/>
./gradlew run       - this will build and run POC (first time will have to pull all dependencies from maven repo)<br/>
&nbsp;&nbsp;&nbsp;&nbsp; the results of the tests are stored in the test-output folder<br/>

./gradlew eclipse   - this will download source packages for dependencies and setup the files so you can import project into Eclipse<br/>

./gradlew tasks     - this will show all of the tasks available for this project through gradlew<br/>


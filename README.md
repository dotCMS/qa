qa
==
Private repo for Quality Assurance artifacts.

Initial artifacts will be Java code utilizing Selenium web driver.

Eventually will include configuration management scripting and automated testing scripts.


To build and use Selenium Web Driver Test Suite:
------------------------------------------------
Setup a dotcms instance running https://s3.amazonaws.com/qa.dotcms.com/starters/3.2_qastarter_v.0.4b.zip instead of the normal starter.zip

Ensure you have a valid dotCMS license for your server.  Some of the tests require enterprise functionality in order to run.

Make sure your host file has entries for the following:
* qademo.dotcms.com
* m.qademo.dotcms.com
* qashared.dotcms.com
* qahost01.dotcms.com
* qahost02.dotcms.com
* qahost03.dotcms.com
* qahost04.dotcms.com
* qahost05.dotcms.com
* qahost06.dotcms.com
* qahost07.dotcms.com

Verify that the properties in src/main/resources/qa.properties are correct.  Especially focus on the server port numbers.

For successful run of the UserTests, you will need to build the com.dotcms.rest.qa_automation plugin:
	cd plugins/com.dotcms.rest.qa_automation
	./gradlew jar
	deploy resulting jar file into your dotcms instance
	
Execute the following from within the repo root directory:<br/>
./gradlew           - this will pull down gradle locally<br/>
./gradlew run       - this will build and run automated tests (first time will have to pull all dependencies from maven repo)<br/>
&nbsp;&nbsp;&nbsp;&nbsp; the results of the tests are stored in the test-output folder<br/>

./gradlew eclipse   - this will download source packages for dependencies and setup the files so you can import project into Eclipse<br/>

./gradlew tasks     - this will show all of the tasks available for this project through gradlew<br/>


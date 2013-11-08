qa
==
Private repo for Quality Assurance artifacts.

Initial artifacts will be Java code utilizing Selenium web driver.

Eventually will include configuration management scripting and automated testing scripts.


To build and use Proof Of Concept:
----------------------------------
Execute the following from within the repo root directory:<br/>
./gradlew           - this will pull down gradle locally<br/>
./gradlew run       - this will build and run POC (first time will have to pull all dependencies from maven repo)<br/>
&nbsp;&nbsp;&nbsp;&nbsp;By default tries to connect to http://demo.dotcms.com/ (can change in src-conf/POC/POC.properties)<br/>
./gradlew eclipse   - this will download source packages for dependencies and setup the files so you can import project into Eclipse<br/>
./gradlew tasks     - this will show all of the tasks available for this project through gradlew<br/>


# .bashrc
export DISPLAY=:99

if [ -e /home/fs/dotcms/automation/status/pullnightlybuild.tmp ]
then
  echo "pullnightlybuild.sh still running - stopping now"
  exit 0
fi

if [ -e /home/fs/dotcms/automation/status/nightlyregressiontests.tmp ]
then
  echo "nightlyregressiontests.sh already running - stopping now"
  exit 0
else
  echo $timestampprefix > /home/fs/dotcms/automation/status/nightlyregressiontests.tmp
fi

cd /home/fs/dotcms/automation/queuefortesting

# create test directory structure and unzip/tar archives
for filename in *
do
	parentdirectory=${filename%.*}
	extension=${filename##*.}
	fulldirectory="/home/fs/dotcms/automation/tests/${parentdirectory}/${extension}"

#	echo "filename="${filename}
#	echo "parentdirectory="${parentdirectory}
#	echo "extension="${extension}
#	echo "fulldirectory="${fulldirectory}

	mkdir -p "${fulldirectory}"

	if [ ${extension} == "zip" ]
	then
		pushd "${fulldirectory}"
		unzip ../../../queuefortesting/"${filename}"
		popd
	fi

	if [ ${extension} == "targz" ]
	then
		pushd "${fulldirectory}"
		tar -xvf ../../../queuefortesting/"${filename}"
		popd
	fi
done

# compare contents of tar and zip archives to ensure they are the same
for filename in *.targz
do
	testdirectory=${filename%.*}
	resultsdirectory="/home/fs/dotcms/automation/results/${testdirectory}"
	tardirectory="/home/fs/dotcms/automation/tests/${testdirectory}/targz"
	zipdirectory="/home/fs/dotcms/automation/tests/${testdirectory}/zip"

	echo "***filename=${filename}"
	echo "testdirectory=${testdirectory}"
	echo "resultsdirectory=${resultsdirectory}"
	echo "tardirectory=${tardirectory}"
	echo "zipdirectory=${zipdirectory}"

	mkdir -p "${resultsdirectory}"

	diff -r "${tardirectory}" "${zipdirectory}" >"${resultsdirectory}/archivedifftestresults.txt"

	# remove zip
	rm -rf "${zipdirectory}"
done



# update qa repo to pull latest testing suite
pushd /home/fs/dotcms/automation/qarepo
git pull
wait
./gradlew installapp
wait
popd

# Run testng/selenium test against dotcms running h2 DB
for filename in *.targz
do
	testdirectory=${filename%.*}
	resultsdirectory="/home/fs/dotcms/automation/results/${testdirectory}"
	testdirectory="/home/fs/dotcms/automation/tests/${testdirectory}"
	dotcmsdir="${testdirectory}/h2_targz"

	#	Create copy of tar dir to use for testing
	cp -a "${testdirectory}/targz" "${dotcmsdir}"
	#	Replace starter with QA Starter
	cp /home/fs/dotcms/starters/3.0_qastarter_v.0.4a.zip "${dotcmsdir}/dotserver/tomcat-7.0.42/webapps/ROOT/starter.zip"
	
	#	Start dotcms server and wait for it to finish starting up
	pushd "${dotcmsdir}"
	bin/startup.sh
	sleep 30
	logcount=`grep -c "startup.StartupTasksExecutor: Finishing upgrade tasks" "./dotserver/tomcat-7.0.42/webapps/ROOT/dotsecure/logs/dotcms.log"`
	while [ $logcount -lt 1 ]
	do
		echo "sleeping..."
		sleep 10
		logcount=`grep -c "startup.StartupTasksExecutor: Finishing upgrade tasks" "./dotserver/tomcat-7.0.42/webapps/ROOT/dotsecure/logs/dotcms.log"`
	done
	popd
	echo "logcount=$logcount"

	#	Run testng/selenium tests
	browser=FIREFOX
	database=H2
	language=en
	country=US
	pushd /home/fs/dotcms/automation/qarepo/build/install/qarepo
	export JAVA_OPTS="-Dtestrail.RunPrefix=NightlyRun_${database}_${browser}_${language}_${country}_ -DbrowserToTarget=${browser} -Duser.language=${language} -Duser.country=${country}"
	bin/qarepo  -testjar lib/qarepo-0.1.jar -listener com.dotcms.qa.testng.listeners.TestRunCreator.class,com.dotcms.qa.testng.listeners.TestResultReporter.class -d "${resultsdirectory}/testngresults_${database}_${browser}_${language}_${country}"
	mv ./qa.log "${resultsdirectory}/testngresults_${database}_${browser}_${language}_${country}/qa.log"
	wait
	popd

	language=zh
	country=CN
	pushd /home/fs/dotcms/automation/qarepo/build/install/qarepo
	export JAVA_OPTS="-Dtestrail.RunPrefix=NightlyRun_${database}_${browser}_${language}_${country}_ -DbrowserToTarget=${browser} -Duser.language=${language} -Duser.country=${country}"
	bin/qarepo  -testjar lib/qarepo-0.1.jar -listener com.dotcms.qa.testng.listeners.TestRunCreator.class,com.dotcms.qa.testng.listeners.TestResultReporter.class -d "${resultsdirectory}/testngresults_${database}_${browser}_${language}_${country}"
	mv ./qa.log "${resultsdirectory}/testngresults_${database}_${browser}_${language}_${country}/qa.log"
	wait
	popd

	#	Stop dotcms server
	pushd "${dotcmsdir}"
	bin/shutdown.sh
	wait
	popd

	#	Move log files
	mv "${dotcmsdir}/dotserver/tomcat-7.0.42/logs/" "${resultsdirectory}/tomcatlogs"
	mv "${dotcmsdir}/dotserver/tomcat-7.0.42/webapps/ROOT/dotsecure/logs/" "${resultsdirectory}/dotcmslogs"

	#	Cleanup dir
	rm -rf "${dotcmsdir}"

	rm -rf "${testdirectory}"

	#	Remove from queuefortesting
	rm "${filename%.*}.targz"
	rm "${filename%.*}.zip"
done


rm /home/fs/dotcms/automation/status/nightlyregressiontests.tmp

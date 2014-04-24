# .bashrc
timestampprefix=$(date +%Y%m%d_%H%M%S)
jsonfilename=$timestampprefix.json

if [ -e /home/fs/dotcms/automation/status/pullnightlybuild.tmp ]
then
  echo "pullnightlybuild.sh already running - stopping now"
  exit 0
else
  echo $timestampprefix > /home/fs/dotcms/automation/status/pullnightlybuild.tmp
fi

cd /home/fs/dotcms/nightlybuilds/

curl http://dotcms.com/api/content/query/+structureName:DotcmsNightlyBuilds%20+conhost:SYSTEM_HOST/limit/1 2>/dev/null > $jsonfilename;

inode=$(cat $jsonfilename | python -c 'import sys, json; print json.load(sys.stdin)["contentlets"][0]["identifier"]')
# following line works but prefer python method for easier extensibility
#inode=$(echo x=`curl http://dotcms.com/api/content/query/+structureName:DotcmsNightlyBuilds%20+conhost:SYSTEM_HOST/limit/1 2>/dev/null` ";" "x['contentlets'][0]['identifier']" | node -p )

commitnumber=$(cat $jsonfilename | python -c 'import sys, json; print json.load(sys.stdin)["contentlets"][0]["commitNumber"]')
filetimestamp=$(cat $jsonfilename | python -c 'import sys, json; print json.load(sys.stdin)["contentlets"][0]["timestamp"].replace(" ", "_").replace(":", "")')
version=$(cat $jsonfilename | python -c 'import sys, json; print json.load(sys.stdin)["contentlets"][0]["version"]')

#echo "filetimestamp=${filetimestamp}"

if [ -e dotcms_"$version"_"$filetimestamp"_$commitnumber.targz ]
then
	echo "targz file already exists - nothing to download"
else
	wget -O dotcms_"$version"_"$filetimestamp"_$commitnumber.targz http://dotcms.com/contentAsset/raw-data/$inode/targz	
	#echo dotcms_"$version"_"$filetimestamp"_$commitnumber > /home/fs/dotcms/automation/queuefortesting/dotcms_"$version"_"$filetimestamp"_$commitnumber.targz
	cp dotcms_"$version"_"$filetimestamp"_$commitnumber.targz /home/fs/dotcms/automation/queuefortesting/dotcms_"$version"_"$filetimestamp"_$commitnumber.targz
fi

if [ -e dotcms_"$version"_"$filetimestamp"_$commitnumber.zip ]
then
	echo "zip file already exists - nothing to download"
else
	wget -O dotcms_"$version"_"$filetimestamp"_$commitnumber.zip http://dotcms.com/contentAsset/raw-data/$inode/zip	
	#echo dotcms_"$version"_"$filetimestamp"_$commitnumber > /home/fs/dotcms/automation/queuefortesting/dotcms_"$version"_"$filetimestamp"_$commitnumber.zip
	cp dotcms_"$version"_"$filetimestamp"_$commitnumber.zip /home/fs/dotcms/automation/queuefortesting/dotcms_"$version"_"$filetimestamp"_$commitnumber.zip
fi

rm $jsonfilename
rm /home/fs/dotcms/automation/status/pullnightlybuild.tmp
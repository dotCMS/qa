#!/bin/bash

# expects DOTCMS_VERSION to be set prior to executing

env

export LASTCOMMIT=$(cat buildinfo.txt | python -c 'import sys, json; print json.load(sys.stdin)["contentlets"][0]["commitNumber"]')

curl http://dotcms.com/api/content/query/+structureName:DotcmsNightlyBuilds%20+conHost:SYSTEM_HOST%20+DotcmsNightlyBuilds.version:3.2/orderby/moddate%20desc/limit/1 2>/dev/null > buildinfo.txt

export DOTCMS_ZIP_URL=http://dotcms.com$(cat buildinfo.txt | python -c 'import sys, json; print json.load(sys.stdin)["contentlets"][0]["zip"]')

export DOTCMS_TAR_GZ_URL=http://dotcms.com$(cat buildinfo.txt | python -c 'import sys, json; print json.load(sys.stdin)["contentlets"][0]["targz"]')

export DOTCMS_COMMIT=$(cat buildinfo.txt | python -c 'import sys, json; print json.load(sys.stdin)["contentlets"][0]["commitNumber"]')

# only do something if commit is different than the last commit processed
echo "LASTCOMMIT = ${LASTCOMMIT}"
echo "DOTCMS_COMMIT = ${DOTCMS_COMMIT}"
echo "DOTCMS_ZIP_URL = ${DOTCMS_ZIP_URL}"
echo "DOTCMS_TAR_GZ_URL = ${DOTCMS_TAR_GZ_URL}"
if [ "$LASTCOMMIT" != "$DOTCMS_COMMIT" ]; then
  echo 'calling saveParams'
  ./saveParams.sh
  echo 'returned from saveParams'
else
  echo 'INFO - Since commit already tested - not triggering builds'
  if [ -f params ];
    then
    rm params
  fi
fi
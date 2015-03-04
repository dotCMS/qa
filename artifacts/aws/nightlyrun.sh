#!/bin/sh

# expects DOTCMS_VERSION to be set prior to executing

env

LASTCOMMIT=$(cat buildinfo.txt | python -c 'import sys, json; print json.load(sys.stdin)["contentlets"][0]["commitNumber"]')

curl http://dotcms.com/api/content/query/+structureName:DotcmsNightlyBuilds%20+conHost:SYSTEM_HOST%20+DotcmsNightlyBuilds.version:${DOTCMS_VERSION}/orderby/moddate%20desc/limit/1 2>/dev/null > buildinfo.txt

ZIP_URL=http://dotcms.com$(cat buildinfo.txt | python -c 'import sys, json; print json.load(sys.stdin)["contentlets"][0]["zip"]')

TAR_GZ_URL=http://dotcms.com$(cat buildinfo.txt | python -c 'import sys, json; print json.load(sys.stdin)["contentlets"][0]["targz"]')

COMMIT=$(cat buildinfo.txt | python -c 'import sys, json; print json.load(sys.stdin)["contentlets"][0]["commitNumber"]')

# only do something if commit is different than the last commit processed
if [ "$LASTCOMMIT" != "$COMMIT" ]; then
  echo "DOTCMS_VERSION=${DOTCMS_VERSION}" > params
  echo "ZIP_URL=${ZIP_URL}" >> params
  echo "TAR_GZ_URL=${TAR_GZ_URL}" >> params
  echo "COMMIT=${COMMIT}" >> params
else
  if [ -f params ];
    then
    rm params
  fi
fi
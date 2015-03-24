#!/bin/sh

# expects DOTCMS_VERSION to be set prior to executing

env

LASTCOMMIT=$(cat buildinfo.txt | python -c 'import sys, json; print json.load(sys.stdin)["contentlets"][0]["commitNumber"]')

#curl http://dotcms.com/api/content/query/+structureName:DotcmsNightlyBuilds%20+conHost:SYSTEM_HOST%20+DotcmsNightlyBuilds.version:${DOTCMS_VERSION}/orderby/moddate%20desc/limit/1 2>/dev/null > buildinfo.txt
curl http://dotcms.com/api/content/query/+structureName:DotcmsNightlyBuilds%20+conHost:SYSTEM_HOST%20+DotcmsNightlyBuilds.version:3.1/orderby/moddate%20desc/limit/1 2>/dev/null > buildinfo.txt

DOTCMS_ZIP_URL=http://dotcms.com$(cat buildinfo.txt | python -c 'import sys, json; print json.load(sys.stdin)["contentlets"][0]["zip"]')

DOTCMS_TAR_GZ_URL=http://dotcms.com$(cat buildinfo.txt | python -c 'import sys, json; print json.load(sys.stdin)["contentlets"][0]["targz"]')

DOTCMS_COMMIT=$(cat buildinfo.txt | python -c 'import sys, json; print json.load(sys.stdin)["contentlets"][0]["commitNumber"]')

# only do something if commit is different than the last commit processed
if [ "$LASTCOMMIT" != "$DOTCMS_COMMIT" ]; then
  echo "DOTCMS_VERSION=${DOTCMS_VERSION}" > params
  echo "DOTCMS_ZIP_URL=${DOTCMS_ZIP_URL}" >> params
  echo "DOTCMS_TAR_GZ_URL=${DOTCMS_TAR_GZ_URL}" >> params
  echo "DOTCMS_COMMIT=${DOTCMS_COMMIT}" >> params
  echo "QA_SERVER_STATUS_URL=s3://qa.dotcms.com/testautomation/commFiles/${BUILD_TAG}.status" >> params
  echo "QA_SERVER_IP_URL=s3://qa.dotcms.com/testautomation/commFiles/${BUILD_TAG}.ip" >> params
else
  if [ -f params ];
    then
    rm params
  fi
fi
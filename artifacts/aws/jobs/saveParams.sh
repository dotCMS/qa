#!/bin/bash

echo "DOTCMS_VERSION=${DOTCMS_VERSION}" > params
echo "QA_Milestone=${QA_Milestone}" >> params
echo "DOTCMS_ZIP_URL=${DOTCMS_ZIP_URL}" >> params
echo "DOTCMS_TAR_GZ_URL=${DOTCMS_TAR_GZ_URL}" >> params
echo "DOTCMS_COMMIT=${DOTCMS_COMMIT}" >> params
echo "QA_SERVER_STATUS_URL=s3://qa.dotcms.com/testautomation/commFiles/${BUILD_TAG}.status" >> params
echo "QA_SERVER_IP_URL=s3://qa.dotcms.com/testautomation/commFiles/${BUILD_TAG}.ip" >> params
echo "QA_SERVER_RECEIVING_STATUS_URL=s3://qa.dotcms.com/testautomation/commFiles/${BUILD_TAG}_receiving.status" >> params
echo "QA_SERVER_RECEIVING_IP_URL=s3://qa.dotcms.com/testautomation/commFiles/${BUILD_TAG}_receiving.ip" >> params
echo "QA_StarterURL=${QA_StarterURL}" >> params
echo "QA_Legacy_Index_Setting=${QA_Legacy_Index_Setting}" >> params
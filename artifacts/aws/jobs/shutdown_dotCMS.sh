#!/bin/bash

echo "DOTCMS_SERVER_IP = ${DOTCMS_SERVER_IP}"
echo "QA_SERVER_IP_URL = ${QA_SERVER_IP_URL}"

if [ -z "${DOTCMS_SERVER_IP}" -a  -n "${QA_SERVER_IP_URL}" ]
then
	echo "Looking up DOTCMS_SERVER_IP from QA_SERVER_IP_URL"
	aws s3 cp ${QA_SERVER_IP_URL} ./ip.txt
	while [ ! -f ./ip.txt ]
	do
		echo "waiting for DOTCMS_SERVER_IP file"
		sleep 30
		aws s3 cp ${QA_SERVER_IP_URL} ./ip.txt
	done
	export DOTCMS_SERVER_IP=$(cat ./ip.txt)
fi

echo "DOTCMS_SERVER_IP = ${DOTCMS_SERVER_IP}"

echo 'Shutting down dotCMS server'
curl -I http://${DOTCMS_SERVER_IP}:8080/shutdown.jsp

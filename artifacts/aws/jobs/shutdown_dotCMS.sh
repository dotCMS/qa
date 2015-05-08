#!/bin/bash

echo 'Shutting down dotCMS server'
curl -I http://${DOTCMS_SERVER_IP}:8080/shutdown.jsp

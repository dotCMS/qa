#!/bin/sh
set -e

etchostname=`cat /etc/hostname`
#if [ `echo "$etchostname" | grep -c '\.'` -eq 0 ]
#then
#    echo "ERROR: /etc/hostname does not contain a fully qualified domain name."
#    echo "edit /etc/hostname and try again"
#    exit 1
#fi

if [ `grep 127.0.0.1 /etc/hosts | grep -c "$etchostname"` -eq 0 ]
then
    echo "Adding hostname to 127.0.0.1 in /etc/hosts"
    sudo sed -i -r "s/^127.0.0.1([ \t].*)/127.0.0.1 $etchostname qademo.dotcms.com m.qademo.dotcms.com qashared.dotcms.com qahost01.dotcms.com qahost02.dotcms.com qahost03.dotcms.com\\1/g" /etc/hosts
fi

hostname=`hostname -f` || true
if [ "$hostname" != "$etchostname" ]
then
    echo "Setting hostname to $etchostname"
    sudo hostname "$etchostname"
fi

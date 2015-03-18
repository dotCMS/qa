#!/bin/sh
set -e

etchostname=`cat /etc/hostname`

if [ `grep 127.0.0.1 /etc/hosts | grep -c "$etchostname"` -eq 0 ]
then
    echo "Adding hostname to 127.0.0.1 in /etc/hosts"
    sudo sed -i -r "s/^127.0.0.1([ \t].*)/127.0.0.1 $etchostname qademo.dotcms.com m.qademo.dotcms.com qashared.dotcms.com qahost01.dotcms.com qahost02.dotcms.com qahost03.dotcms.com qahost04.dotcms.com qahost05.dotcms.com qahost06.dotcms.com qahost07.dotcms.com\\1/g" /etc/hosts
fi

hostname=`hostname -f` || true
if [ "$hostname" != "$etchostname" ]
then
    echo "Setting hostname to $etchostname"
    sudo hostname "$etchostname"
fi

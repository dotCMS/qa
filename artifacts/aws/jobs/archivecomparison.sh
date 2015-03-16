#!/bin/sh

# ensure clean workspace
rm -rf *

# expects these two environment variables to be set
echo zip: $DOTCMS_ZIP_URL
echo tar: $DOTCMS_TAR_GZ_URL

pwd

env

wget -q -O temp.zip $DOTCMS_ZIP_URL
ls -la temp.zip
md5sum temp.zip

wget -q -O temp.targz $DOTCMS_TAR_GZ_URL
ls -la temp.targz
md5sum temp.targz

mkdir zipcontents
cd zipcontents
unzip ../temp.zip
cd ..

mkdir tarcontents
cd tarcontents
tar -xvf ../temp.targz
cd ..

diff -r tarcontents zipcontents


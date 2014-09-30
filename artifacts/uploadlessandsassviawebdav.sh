HOST="ubuntuavatar:8080"
USEROPT="--user admin@dotcms.com:admin"
REPODIR="/Users/brent/dotcms/repos/qa"

curl -i $USEROPT -X MKCOL http://$HOST/webdav/autopub/demo.dotcms.com/qa

# LESS folders and files
curl -i $USEROPT -T $REPODIR/artifacts/LESS/testless_originalexpected.png http://$HOST/webdav/autopub/demo.dotcms.com/qa/
curl -i $USEROPT -T $REPODIR/artifacts/LESS/testless.htm http://$HOST/webdav/autopub/demo.dotcms.com/qa/
curl -i $USEROPT -X MKCOL http://$HOST/webdav/autopub/demo.dotcms.com/qa/less
curl -i $USEROPT -T $REPODIR/artifacts/LESS/less/bodystyles.less http://$HOST/webdav/autopub/demo.dotcms.com/qa/less/
curl -i $USEROPT -T $REPODIR/artifacts/LESS/less/largeareastyles.less http://$HOST/webdav/autopub/demo.dotcms.com/qa/less/
curl -i $USEROPT -T $REPODIR/artifacts/LESS/less/smallareastyles.less http://$HOST/webdav/autopub/demo.dotcms.com/qa/less/
curl -i $USEROPT -T $REPODIR/artifacts/LESS/less/styles.less http://$HOST/webdav/autopub/demo.dotcms.com/qa/less/
curl -i $USEROPT -X MKCOL http://$HOST/webdav/autopub/demo.dotcms.com/qa/less/variables
curl -i $USEROPT -T $REPODIR/artifacts/LESS/less/variables/variables.less http://$HOST/webdav/autopub/demo.dotcms.com/qa/less/variables/
curl -i $USEROPT -X MKCOL http://$HOST/webdav/autopub/demo.dotcms.com/qa/less/variables/colors
curl -i $USEROPT -T $REPODIR/artifacts/LESS/less/variables/colors/colors.less http://$HOST/webdav/autopub/demo.dotcms.com/qa/less/variables/colors/
curl -i $USEROPT -X MKCOL http://$HOST/webdav/autopub/demo.dotcms.com/qa/less/variables/includes
curl -i $USEROPT -T $REPODIR/artifacts/LESS/less/variables/includes/colors_import.less http://$HOST/webdav/autopub/demo.dotcms.com/qa/less/variables/includes/
curl -i $USEROPT -T $REPODIR/artifacts/LESS/less/variables/includes/sizes_import.less http://$HOST/webdav/autopub/demo.dotcms.com/qa/less/variables/includes/
curl -i $USEROPT -X MKCOL http://$HOST/webdav/autopub/demo.dotcms.com/qa/less/variables/sizes
curl -i $USEROPT -T $REPODIR/artifacts/LESS/less/variables/sizes/sizes.less http://$HOST/webdav/autopub/demo.dotcms.com/qa/less/variables/sizes/
curl -i $USEROPT -T $REPODIR/artifacts/SASS/testsass_originalexpected.png http://$HOST/webdav/autopub/demo.dotcms.com/qa/
curl -i $USEROPT -T $REPODIR/artifacts/SASS/testsass.htm http://$HOST/webdav/autopub/demo.dotcms.com/qa/

# SASS folders and files
curl -i $USEROPT -X MKCOL http://$HOST/webdav/autopub/demo.dotcms.com/qa/sass
curl -i $USEROPT -T $REPODIR/artifacts/SASS/sass/_bodystyles.scss http://$HOST/webdav/autopub/demo.dotcms.com/qa/sass/
curl -i $USEROPT -T $REPODIR/artifacts/SASS/sass/_largeareastyles.scss http://$HOST/webdav/autopub/demo.dotcms.com/qa/sass/
curl -i $USEROPT -T $REPODIR/artifacts/SASS/sass/_smallareastyles.scss http://$HOST/webdav/autopub/demo.dotcms.com/qa/sass/
curl -i $USEROPT -T $REPODIR/artifacts/SASS/sass/styles.scss http://$HOST/webdav/autopub/demo.dotcms.com/qa/sass/
curl -i $USEROPT -X MKCOL http://$HOST/webdav/autopub/demo.dotcms.com/qa/sass/variables
curl -i $USEROPT -T $REPODIR/artifacts/SASS/sass/variables/_variables.scss http://$HOST/webdav/autopub/demo.dotcms.com/qa/sass/variables/
curl -i $USEROPT -X MKCOL http://$HOST/webdav/autopub/demo.dotcms.com/qa/sass/variables/colors
curl -i $USEROPT -T $REPODIR/artifacts/SASS/sass/variables/colors/_colors.scss http://$HOST/webdav/autopub/demo.dotcms.com/qa/sass/variables/colors/
curl -i $USEROPT -X MKCOL http://$HOST/webdav/autopub/demo.dotcms.com/qa/sass/variables/includes
curl -i $USEROPT -T $REPODIR/artifacts/SASS/sass/variables/includes/_colors_import.scss http://$HOST/webdav/autopub/demo.dotcms.com/qa/sass/variables/includes/
curl -i $USEROPT -T $REPODIR/artifacts/SASS/sass/variables/includes/_sizes_import.scss http://$HOST/webdav/autopub/demo.dotcms.com/qa/sass/variables/includes/
curl -i $USEROPT -X MKCOL http://$HOST/webdav/autopub/demo.dotcms.com/qa/sass/variables/sizes
curl -i $USEROPT -T $REPODIR/artifacts/SASS/sass/variables/sizes/_sizes.scss http://$HOST/webdav/autopub/demo.dotcms.com/qa/sass/variables/sizes/

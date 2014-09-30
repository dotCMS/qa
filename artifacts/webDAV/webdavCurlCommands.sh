# Examples adapted from here:  http://italiangrid.github.io/storm/documentation/webdav-guide/3.0.0/#usingcurls


# get - can use a range header for a partial get
curl -i --user admin@dotcms.com:admin http://localhost:8080/webdav/autopub/demo.dotcms.com/qa/less/styles.less

# put - by default overwrites content (implied 'Overwrite: T' header) to avoid overwriting file use 'Overwrite: F' header
#       from command line
curl -i --user admin@dotcms.com:admin -X PUT http://localhost:8080/webdav/autopub/demo.dotcms.com/qa/testfile.txt --data-ascii "Hello World"
#       from existing file - without specifying the file name in URL
curl -i --user admin@dotcms.com:admin -T ./working.sql http://localhost:8080/webdav/autopub/demo.dotcms.com/qa/
#       from existing file - specifying the file name in URL
curl -i --user admin@dotcms.com:admin -T ./working.sql http://localhost:8080/webdav/autopub/demo.dotcms.com/qa/working2.sql

# make new directory/folder
curl -i --user admin@dotcms.com:admin -X MKCOL http://localhost:8080/webdav/autopub/demo.dotcms.com/qa2

# delete - if a non empty folder is specified, the delete is recursive
curl -i --user admin@dotcms.com:admin -X DELETE http://localhost:8080/webdav/autopub/demo.dotcms.com/qa2

# show available methods via allow header
curl -i --user admin@dotcms.com:admin -X OPTIONS http://localhost:8080/webdav/autopub/demo.dotcms.com/

# head
curl -i --user admin@dotcms.com:admin --head http://localhost:8080/webdav/autopub/demo.dotcms.com/

#get list of all contents - recursively (default "Depth: infinity")
curl -i --user admin@dotcms.com:admin -X PROPFIND http://localhost:8080/webdav/autopub/demo.dotcms.com/ -H "Content-Type: text/xml"
# gets list of all contents - for first level
curl -i --user admin@dotcms.com:admin -X PROPFIND http://localhost:8080/webdav/autopub/demo.dotcms.com/ -H "Content-Type: text/xml" -H "Depth: 1"
# The body content is specified through the data-ascii option, that, first of all, contains the XML header: --data-ascii "<?xml version='1.0' encoding='utf-8'?>..."
# To obtain the list of names of all the resource properties complete it with:
#	<propfind xmlns='DAV:'><propname/></propfind>
# To obtain the value of a single property complete it with:
#	<propfind xmlns='DAV:'><prop>property-name</prop></propfind>
# To obtain all the property values complete it with:
#	<propfind xmlns='DAV:'><allprop/></propfind>

# copy - by default is suppossed to be recursive but it does not appear that dotCMS behaves that way - also seems that dotCMS ignores the "Depth: infinity" header
# use -H "Overwrite: T" if you want to overwrite if destination already exists
curl -i --user admin@dotcms.com:admin -X COPY http://localhost:8080/webdav/autopub/demo.dotcms.com/qa/ -H "Destination: http://localhost:8080/webdav/autopub/demo.dotcms.com/qa3/"

# move
curl -i --user admin@dotcms.com:admin -X MOVE http://localhost:8080/webdav/autopub/demo.dotcms.com/qa3/ -H "Destination: http://localhost:8080/webdav/autopub/demo.dotcms.com/qa4/"

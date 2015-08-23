#! /usr/bin/python
print "Context-type: text/html\n\n"
import SkyRealmDatabaseAPI
import cgi
import json
cgi.FieldStorage()
arguments = cgi.FieldStorage()
username = arguments.getvalue("username")



if username == None:
    print "parameter missing!"
else:
    print json.dumps(SkyRealmDatabaseAPI.list_pending_friends(username))
#! /usr/bin/python
print "Context-type: text/html\n\n"

import SkyRealmDatabaseAPI
import cgi
cgi.FieldStorage()
arguments = cgi.FieldStorage()
username = arguments.getvalue("username")
password = arguments.getvalue("password")
email = arugments.getvalue("email")

if username == None and email == None or password == None:
    print "parameter missing!"
else:
    SkyRealmDatabaseAPI.login_script(username, password)

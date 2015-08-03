#! /usr/bin/python


import cgi
cgi.FieldStorage()
arguments = cgi.FieldStorage()
username = arguments.getvalue("username")
password = arguments.getvalue("password")
email = arugments.getvalue("email")

if username == None or password == None or email == None:
    print "Missing parameters"
else:
    if SkyRealmDatabaseAPI.register_check(username, password):
        print "account created!"
    else:
        print "error:account not created!"

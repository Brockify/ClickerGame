#! /usr/bin/python
print "Context-type: text/html\n\n"

import SkyRealmDatabaseAPI
import cgi
cgi.FieldStorage()
arguments = cgi.FieldStorage()
UserSending = arguments.getvalue("UserSending")
UserReceiving = arguments.getvalue("UserReceiving")

if UserSending == None or UserReceiving == None:
    print "parameter missing!"
else:
    SkyRealmDatabaseAPI.accept_friend_request(UserSending, UserReceiving)
#! /usr/bin/python
print "Context-type: text/html\n\n"
import cgi
import SkyRealmDatabaseAPI
arguments = cgi.FieldStorage()
username = arguments.getvalue("username")
password = arguments.getvalue("password")
email = arguments.getvalue("email")

if username == None or password == None or email == None:
    print "Missing parameters"
else:
    if SkyRealmDatabaseAPI.profanity_filter(username.lower()) == "Profanity!":
        print "Invalid username, username may not contain vulgar language!"
    elif len(password) < 4:
        print "Password must exceed 4 characters!"
    elif len(password) > 16:
        print "Password must be less than 16 characters!"
    elif password == username:
        print "password cannot be the same as your username!"
    else:
        SkyRealmDatabaseAPI.create_account(username, password, email)
        



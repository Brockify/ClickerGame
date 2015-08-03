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
    failuser = True
    UsernameCursor = db.cursor()
    while failuser == True:
        checkUsername = "select Username from PokeWarUsers where username=%s"
        UsernameCursor.execute(checkUsername, username)
        usernameToCheck = UsernameCursor.fetchone()
        if username.lower() == usernameToCheck:
            print "Username is already in use!"
        elif SkyRealmDatabaseAPI.profanity_filter(username.lower()) == "Profanity!":
            print "Invalid username, username may not contain vulgar language!"
        else:
            failuser = False

    failpass = True
    while failpass == True:
        if len(password) < 4:
            print "Password must exceed 4 characters!"
        elif len(password) > 16:
            print "Password must be less than 16 characters!"
        if password == username:
            print "password cannot be the same as your username!"
        else:
            failpass = False
    if failuser == False and failpass == False:
        print "Account Created!"

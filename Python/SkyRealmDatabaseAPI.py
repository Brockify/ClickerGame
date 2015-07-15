#!/usr/bin/python
import MySQLdb

db = MySQLdb.connect("173.254.28.39", "skyrealm","AndrewBrock@2013","skyrealm_FindMyPeeps")
CUR = db.cursor()

def create_account():
    failuser = True
    UsernameCursor = db.cursor()
    while failuser == True:
        username = raw_input("Type in a username: ")
        checkUsername = "select Username from PokeWarUsers where username=%s"
        UsernameCursor.execute(checkUsername, username)
        usernameToCheck = UsernameCursor.fetchone()
        if username.lower() == usernameToCheck:
            print "Username is already in use!"
        else:
            failuser = False

    failpass = True
    while failpass == True:
        password = raw_input("Type in a password: ")
        if len(password) < 4:
            print "Password must exceed 4 characters!"
        else:
            failpass = False
    email = raw_input("Type in your email: ")
    query = "insert into PokeWarUsers(Username, OriginalUsername, Password, Email) values (%s, %s, %s, %s)"
    CUR.execute(query, (username.lower(), username, password, email))

#gets users username
def get_username(id):
    query = "select Username from PokeWarUsers where Username=%s"
    CUR.execute(query, id)
    print CUR.fetchone()[0]
    return CUR.fetchone()[0]

#gets a users email
def get_email(username):
    query = "select Email from PokeWarUsers where Username=%s"
    CUR.execute(query, username)
    return CUR.fetchone()[0]

def get_password(username):
    query = "select Password from PokeWarUsers where Username=%s"
    CUR.execute(query, username)
    return CUR.fetchone()[0]

print get_email("brock")
print get_password("brock")
#!/usr/bin/python
import MySQLdb

db = MySQLdb.connect("173.254.28.39", "skyrealm","AndrewBrock@2013","skyrealm_FindMyPeeps")
CUR = db.cursor()

#function that asks user for their username and gives them there password
def get_user_password():
    username = ""
    while username != "exit":
        username = raw_input("What is your username?(Type exit to exit program): ")
        query = "select all Password from iOweYouUser where Username=%s"
        response = str(CUR.execute(query, username))
        if len(response) != None:
            for x in CUR.fetchall():
                print("Your password is: " + x[0])
                break
            else:
                print("ERROR: Invalid username.")




def create_account():
    failuser = True
    UsernameCursor = db.cursor()
    while failuser == True:
        username = raw_input("Type in a username: ")
        checkUsername = "select Username from PokeWarUsers %s = username"
        UsernameCursor.execute(checkUsername, username)
        usernameToCheck = UsernameCursor.fetchone()[0]
        for x in usernameToCheck:
            print str(x)

        if username.lower() == str(usernameToCheck):
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


create_account()
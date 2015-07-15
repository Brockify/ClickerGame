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
    username = raw_input("Type in a username: ")
    password = raw_input("Type in a password: ")
    
    query = "insert into PokeWarUsers(Username, OriginalUsername, Password) values (%s, %s, %s)"



    CUR.execute(query, (username.lower(), username, password))


create_account()
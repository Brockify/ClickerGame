#! /usr/bin/python

import MySQLdb
import urllib2

print "Content-Type: text/html\n\n"

db = MySQLdb.connect("localhost", "skyrealm","AndrewBrock@2013","skyrealm_WW3")
CUR = db.cursor()

def get_countries():
    countries = urllib2.urlopen("http://www.skyrealmstudio.com/countries.txt").read().split("\n")
    return countries

def add_click_to_country(country):
    sql = "select clicks from countries where country=%s"
    CUR.execute(sql, (country))
    clicks = CUR.fetchone()
    clicks = clicks + 1
    sql = "update Countries set clicks=%s where country=%s"
    CUR.execute(sql, (clicks, country))
    print "updated"
=========================================================================
=========================================================================
def create_account(username, password, email):
    checkUsername = "select Username from PokeWarUsers where username=%s"
    CUR.execute(checkUsername, [username])
    usernameToCheck = CUR.fetchone()
    if usernameToCheck != None:
        print "Username is already in use!"
    checkEmail = "select email from PokeWarUsers where email=%s"
    CUR.execute(checkEmail, [email])
    emailToCheck = CUR.fetchone()
    if emailToCheck != None:
        print "email is already in use!"
    else:
        query = "insert into PokeWarUsers(Username, OriginalUsername, Password, Email) values (%s, %s, %s, %s)"
        CUR.execute(query, (username.lower(), username, password, email))
        print "Success"
def login_script(username, password):
    query = "select username from PokeWarUsers where username=%s"
    CUR.execute(query, [username])
    checkifuserexists = CUR.fetchone()
    if checkifuserexists == None:
        print "Username doesn't exist!"

    else:
        query = "select Password from PokeWarUsers where Username=%s"
        CUR.execute(query, [username])
        checkifpassexists = CUR.fetchone()

        if password == checkifpassexists[0]:
            print "Login Success"

        else:
            print "Login Failed"


        if checkifpassexists == None:
            print "Login Failed"


        else:
            print "Login Success"
def profanity_filter(word):
    profanity = urllib2.urlopen("http://www.skyrealmstudio.com/profanity.txt").read(20000)
    profanity = profanity.split("\n")
    for badword in  profanity:
        if word.find(badword) != -1:
            word = word.replace(badword, "*****")
            return "Profanity!"
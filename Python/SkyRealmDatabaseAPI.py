#!/usr/bin/python
print "Context-type: text/html\n\n"
import MySQLdb
import urllib2
import json



db = MySQLdb.connect("173.254.28.39", "skyrealm","AndrewBrock@2013","skyrealm_PokeWars")

CUR = db.cursor()

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


#gets users username
def get_username(username):
    query = "select Username from PokeWarUsers where username=%s"
    CUR.execute(query, [username])
    checkifuserexists = CUR.fetchone()
    if checkifuserexists != None:
        return True
    else:
        return False


#gets users username
def get_username(username):
    query = "select Username from PokeWarUsers where Username=%s"
    CUR.execute(query, [username])
    checkifuserexists = CUR.fetchone()
    if checkifuserexists!= None:
        return True
    else:
        return False

#gets a users email
def get_email(email):
    query = "select Email from PokeWarUsers where email=%s"
    CUR.execute(query, [email])
    checkifemailexists = CUR.fetchone()
    if checkifemailexists != None:
        return True
    else:
        return False



#gets a users password
def get_password(password):
    query = "select Password from PokeWarUsers where password=%s"
    CUR.execute(query, password)
    checkifpassexists = CUR.fetchone()
    if checkifpassexists != None:
        return True
    else:
        return False

#login script, returns sucess if user can login, returns fail if user cant login
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

#send friend request script, returns "User Doesn't Exist if the receiver doesn't exist", says "Friend Request
#already sent to this person" if they already are pending and "Friend Added" if they were added successfuly
def send_friend_request(UserSending, UserReceiving):
    sql = "select Username from PokeWarUsers where Username=%s"
    CUR.execute(sql, UserReceiving)
    if CUR.fetchone() == None:
        return "User Doesn't exist"
    else:
        sql = "select UserReceiving from PokeWarFriendRequest where UserSending=%s and UserReceiving=%s"
        CUR.execute(sql, (UserSending, UserReceiving))
        if CUR.fetchone() == None:
            sql = "insert into PokeWarFriendRequest(UserSending, UserReceiving) values (%s, %s)"
            CUR.execute(sql, (UserSending, UserReceiving))
            print "Friend request sent"
        else:
            print "Friend request already sent to this person"

#accept_friend_request returns "Friend Added" if friend is added and returns "User Already a friend" to catch errors
def accept_friend_request(UserSending, UserReceiving):
    sql = "select UserSending from PokeWarFriendRequest where UserReceiving=%s"
    potentialfriend = CUR.execute(sql, [UserReceiving])
    if potentialfriend == None:
        print "weird"
    else:
        sql = "insert into PokeWarFriend(LoggedInUser, OtherUser) values (%s, %s)"
        CUR.execute(sql, (UserSending, UserReceiving))
        sql = "insert into PokeWarFriend(OtherUser,LoggedInUser) values (%s, %s)"
        CUR.execute(sql, (UserSending, UserReceiving))
        print "Friend Added"
def list_pending_friends(username):
    sql = "select all UserSending from PokeWarFriendRequest where UserReceiving=%s"
    CUR.execute(sql, [username])
    pendingfriends = CUR.fetchall()
    if pendingfriends == ():
        print "No Friend Requests"
    else:
        for i in pendingfriends:
            print i[0]


#returns "User has no friends" if they don't have any friends and returns a list of users if they do
def list_friends(username):
    sql = "select all OtherUser from PokeWarFriend where LoggedInUser=%s"
    CUR.execute(sql, [username])
    result = []
    friendslist = CUR.fetchall()
    if friendslist == ():
        print "User has no friends"
    else:
        for i in friendslist:
            userDict = {}
            userDict["username"] = i[0]
            result.append(userDict)

    return result
def profanity_filter(word):
    profanity = urllib2.urlopen("http://www.skyrealmstudio.com/profanity.txt").read(20000)
    profanity = profanity.split("\n")
    for badword in  profanity:
        if word.find(badword) != -1:
            word = word.replace(badword, "*****")
            return "Profanity!"



import mysql.connector as connector
import random
import os
from platform import system

days = 31
orders = 100

creditPath = os.path.dirname(os.path.abspath(__file__))
if (system() == "Linux"):
	creditPath += "/"
else:
	creditPath += "\\"

def getCredits(path: str = creditPath + "databaseCredits.txt"):
	databaseCredits = []
	with open(path, "r") as creditsFile:
	    for row in creditsFile:
	        databaseCredits.append(row[:-1])
	return databaseCredits

def randomTime():
	hrs = random.randint(0, 23)
	mins = random.randint(0, 59)
	sec = random.randint(0, 59)
	hrs = "0" + str(hrs) if(hrs < 10) else str(hrs)
	mins = "0" + str(mins) if (mins < 10) else str(mins)
	sec = "0" + str(sec) if(sec < 10) else str(sec)
	return str(hrs + ":" + mins + ":" + sec)

def getValues(days):
	mdate = "2020-01-0" + str(days) if (days < 10) else "2020-01-" + str(days)
	mdate += " " + randomTime()
	return random.randint(1, 8),  random.randint(2, 4) * 100,  random.randint(0, 4), random.randint(0, 2) * 100, mdate

dbCredit = getCredits()
dataBaseConnection = connector.connect(host = dbCredit[0],
	user = dbCredit[1],
	passwd = dbCredit[2],
)

sqlcursor = dataBaseConnection.cursor()
sqlcursor.execute("USE coffeeBreaker;")

def clearDatabase():
	sql = "truncate coffeeBreaker.CoffeeBreakerDataTable;"
	sqlcursor.execute(sql)
	dataBaseConnection.commit()

def insertDatabase():
	print("WORKING...")
	for j in range(days):
		for i in orders:
			sql = "INSERT INTO CoffeeBreakerDataTable (coffeeID, water, sugar, milk, date) VALUES (%s, %s, %s, %s, %s)"
			sqlcursor.execute(sql, getValues(j+1))
			dataBaseConnection.commit()
	print("DONE!")

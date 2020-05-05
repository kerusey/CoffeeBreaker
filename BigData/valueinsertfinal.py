import mysql.connector as connector
import random
import os
days = 1
orders = 20


def getCredits(path: str = os.path.dirname(os.path.abspath(__file__)) + "\databaseCredits.txt"):
	databaseCredits = []
	with open(path, "r") as creditsFile:
	    for row in creditsFile:
	        databaseCredits.append(row[:-1])
	return databaseCredits

'''def randomTime():
	hrs = random.randint(0, 24)
	mins = random.randint(0, 60)
	if(int(mins) < 10):
		mins = "0" + str(mins)
	if(int(hrs) < 10):
		hrs = "0" + str(hrs)
	return str(str(hrs) + ":" + str(mins))'''

def getValues(days):
	coffee = 8
	id_ = random.randint(1,8)
	sugar = random.randint(0,4)
	water = random.randint(2,4) * 100
	milk = random.randint(0,2) * 100
	'''if (days < 10):
		mdate = "2020.01.0" + str(days)
	else:
		mdate = "2020.01." + str(days)'''

	return id_,  water, sugar, milk




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
	for j in range(days):
		for i in range(orders):
			sql = "INSERT INTO CoffeeBreakerDataTable ( coffeeID, water, sugar, milk) VALUES (%s, %s, %s, %s)"
			sqlcursor.execute(sql, getValues(j+1))
			dataBaseConnection.commit()








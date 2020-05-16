import mysql.connector as connector
import random
import os
from platform import system

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

dbCredit = getCredits()
dataBaseConnection = connector.connect(host = dbCredit[0],
user = dbCredit[1],
passwd = dbCredit[2],
)

sqlcursor = dataBaseConnection.cursor()
sqlcursor.execute("USE coffeeBreaker;")#use

def clearDatabase():
	sql = "truncate coffeeBreaker.CoffeeBreakerDataTable;"
	sqlcursor.execute(sql)
	dataBaseConnection.commit()
'''
def insertDatabase():
	print("WORKING...")
	for j in range(days):
		for i in range(orders):
			sql = "INSERT INTO CoffeeBreakerDataTable (coffeeID, water, sugar, milk, date) VALUES (%s, %s, %s, %s, %s)"
			sqlcursor.execute(sql, getValues(j+1))
			dataBaseConnection.commit()
	print("DONE!")
'''

#TODO("slovar in bd, json to dict")
def valueInsertion(dataBaseValues: dict):
    sql = "INSERT INTO CoffeeBreakerDataTable (coffeeID, water, sugar, milk) VALUES (%s, %s, %s, %s)"
    listOfData = []
    for item  in dataBaseValues:
        listOfData.append(dataBaseValues[item])
    sqlcursor.execute(sql, tuple(listOfData))
    dataBaseConnection.commit()

def printingBD():
    mycursor = dataBaseConnection.cursor()
    mycursor.execute("SELECT * FROM CoffeeBreakerDataTable")
    myresult = mycursor.fetchall()
    for x in myresult:
        print(x)



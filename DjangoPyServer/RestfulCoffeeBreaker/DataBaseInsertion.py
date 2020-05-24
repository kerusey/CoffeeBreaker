import mysql.connector as connector
import json
import os
from platform import system

creditPath = os.path.dirname(os.path.dirname(os.path.abspath(__file__))) + "/Globals/"

dbCredits = json.load(open(creditPath + "DataBaseCredits.json"))

dataBaseConnection = connector.connect(
    host = dbCredits['host'],
    user = dbCredits['userName'],
    passwd = dbCredits['pass'],
)

sqlcursor = dataBaseConnection.cursor()
sqlcursor.execute("USE coffeeBreaker;")#use

def clearDatabase():
	sql = "truncate coffeeBreaker.CoffeeBreakerDataTable;"
	sqlcursor.execute(sql)
	dataBaseConnection.commit()

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



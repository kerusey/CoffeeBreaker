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
sqlcursor.execute("USE " + dbCredits['dataBaseName'] + ";")

def clearDatabase():
	sql = "truncate coffeeBreaker." + dbCredits['dataTableName'] + ";"
	sqlcursor.execute(sql)
	dataBaseConnection.commit()

def valueInsert(dataBaseValues: dict):
    sql = "INSERT INTO " + dbCredits['dataTableName'] + " (coffeeID, water, sugar, milk) VALUES (%s, %s, %s, %s)"
    listOfData = []
    for item  in dataBaseValues:
        listOfData.append(dataBaseValues[item])
    sqlcursor.execute(sql, tuple(listOfData))
    dataBaseConnection.commit()

def printBataFromBase():
    mycursor = dataBaseConnection.cursor()
    mycursor.execute("SELECT * FROM " + dbCredits['dataTableName'])
    myresult = mycursor.fetchall()
    for x in myresult:
        print(x)

printBataFromBase()

from django.conf import settings
import mysql.connector as connector
import json

dataBaseConnection = connector.connect(
    host = settings.DATA_BASE_CREDITS['host'],
    user = settings.DATA_BASE_CREDITS['userName'],
    passwd = settings.DATA_BASE_CREDITS['pass'],
)

sqlcursor = dataBaseConnection.cursor()
sqlcursor.execute("USE " + settings.DATA_BASE_CREDITS['dataBaseName'] + ";")

def clearDatabase():
	sql = "truncate coffeeBreaker." + settings.DATA_BASE_CREDITS['dataTableName'] + ";"
	sqlcursor.execute(sql)
	dataBaseConnection.commit()

def dropDatabase():
    sqlcursor.execute("DROP TABLE " + settings.DATA_BASE_CREDITS['dataTableName'])
    dataBaseConnection.commit()

def valueInsert(dataBaseValues: dict, header: tuple = ("coffeeID", "water", "sugar", "milk")):
    sql = "INSERT INTO " + settings.DATA_BASE_CREDITS['dataTableName'] + " (coffeeID, water, sugar, milk) VALUES (%s, %s, %s, %s)"
    listOfData = []
    for item  in dataBaseValues:
        listOfData.append(dataBaseValues[item])
    sqlcursor.execute(sql, tuple(listOfData))
    dataBaseConnection.commit()

def printBataFromBase():
    mycursor = dataBaseConnection.cursor()
    mycursor.execute("SELECT * FROM " + settings.DATA_BASE_CREDITS['dataTableName'])
    myresult = mycursor.fetchall()
    for x in myresult:
        print(x)

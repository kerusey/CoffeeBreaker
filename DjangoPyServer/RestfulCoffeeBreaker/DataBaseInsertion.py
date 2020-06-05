from django.conf import settings
import mysql.connector as connector

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

def getDataConvertedToJson(typeof:str = "js"):
	mycursor = dataBaseConnection.cursor()
	mycursor.execute("SELECT * FROM " + settings.DATA_BASE_CREDITS['dataTableName'])
	results = mycursor.fetchall()	
	globalDict = {}
	if(typeof == "js"):
		for row in results:
			currentDict = {
				'name': row[1],
				'xCoord': row[2],
				'yCoord': row[3],
				'clusterID': row[4]
			}
			globalDict['_' + str(row[0])] = currentDict

	else:
		for row in results:
			currentDict = {
				'name': row[1],
				'xCoord': row[2],
				'yCoord': row[3],
				'clusterID': row[4]
			}
			globalDict[int(row[0])] = currentDict

	return globalDict

print(getDataConvertedToJson())

def getNumberOfCoffeeHouses():
	mycursor = dataBaseConnection.cursor()
	mycursor.execute("SELECT * FROM " + settings.DATA_BASE_CREDITS['dataTableName'])
	results = mycursor.fetchall()
	return results

getNumberOfCoffeeHouses()
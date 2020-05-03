# (id, milk, coffee, sugar, water,  mtime, mdate)
import mysql.connector

def getFiltredDict(id, milk, coffee, sugar, water, mtime, mdate):

	mount = {
			'id': id,
			'milk': milk,
			'coffee': coffee,
			'sugar': sugar,
			'water': water,
			'mtime': mtime,
			'mdate': mdate,
			}

	removableElements = []

	for object in mount:
		if (mount[object] == None):
			removableElements.append(object)

	for element in removableElements:
		del(mount[element])
	return mount

def generateCondition(filtredDict:dict):
	condition = ""
	for key in filtredDict.keys():
		condition += key + " = %s AND "
	return condition[:-5]

def getCredits(path:str="databaseCredits.txt"):
	databaseCredits = []
	with open (path, "r") as creditsFile:
		for row in creditsFile:
			databaseCredits.append(row[:-1])

	return databaseCredits

databaseCredits = getCredits()

db = mysql.connector.connect(
	host=databaseCredits[0],
	user=databaseCredits[1],
	passwd=databaseCredits[2]
)
mycursor = db.cursor()
mycursor.execute("USE coffeeBreaker;")

def clearDatabase():
	mycursor.execute("DELETE FROM CoffeeBreakerDataTable")
	db.commit()

def getDatabase():
	sql = "SELECT * FROM CoffeeBreakerDataTable"
	mycursor.execute(sql)
	myresult = mycursor.fetchall()
	for x in myresult:
		print(x)
	return myresult # list of tuples

def getDatabaseByArgument(id:int=None, milk:int=None, coffee:int=None, sugar:int=None, water:int=None, mtime:str=None, mdate:str=None):
	tupl = (id, milk, coffee, sugar, water, mtime, mdate)
	filtredDict = getFiltredDict(*tupl)
	condition = generateCondition(filtredDict)

	sql = "SELECT * FROM CoffeeBreakerDataTable WHERE " + condition
	mycursor.execute(sql, tuple(filtredDict.values()))
	myresult = mycursor.fetchall()
	for x in myresult:
		print(x)
	return myresult # list of tuples

""" getDatabaseByArgument usage: 
	==============
	getDatabaseByArgument(id=6, mdate="15.03.2020")
	will return you all database lines with id=6 and mdate="15.03.2020"
	==============
	tupl = (1, 2, 3, 4, 5, "19:00", "30.01.2020")
	getDatabaseByArgument(*tupl)
	==============
"""

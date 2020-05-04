import integrator
import numpy
import pandas
import mysql.connector

databaseCredits = integrator.getCredits()
database = mysql.connector.connect(
	host=databaseCredits[0],
	user=databaseCredits[1],
	passwd=databaseCredits[2]
)
mycursor = database.cursor()
mycursor.execute("USE coffeeBreaker;")


data = pandas.read_sql(integrator.getDatabaseByArgument(id=6))

data.sort_values(by=['date'],
	ascending=[True]).head()

import numpy
import pandas
import matplotlib.pyplot as plt
import pymysql
import datetime
import json
from sqlalchemy import create_engine
from sklearn.linear_model import LinearRegression
from sklearn.metrics import mean_absolute_error
from sklearn.neural_network import MLPRegressor
from sklearn.ensemble import RandomForestRegressor
from sklearn.neighbors import KNeighborsRegressor
from sklearn.model_selection import GridSearchCV

fromDate = "2020-01-01"
toDate = "2020-01-30"
id = 1
past = start = 7
future = 1

def convertStrToDatetime(date):
	return datetime.date(int(date[0:4]), int(date[5:7]), int(date[8:10]))
	
def summer(data, id:int, date:datetime): # returns coffee, water, sugar, milk summary from filtred database
	result = []
	date = str(date)
	data = data[(data['coffeeID'] == id) & ([str(item)[:-9] == date for item in data['date']])]
	result.append(len(data.coffeeID)*8)
	for header in data:
		if (header == "coffeeID" or header == "id"):
			continue
		if (header == "date"):
			break
		result.append(sum(item for item in data[header]))
	return result

def summerIdByDate(data, id:int, fromDate:datetime, toDate:datetime):
	globalSum = [ ]
	fromDate, toDate = str(fromDate), str(toDate)
	data = data[(data['coffeeID'] == id) & ([str(item)[:-9] >= fromDate for item in data['date']]) & ([str(item)[:-9] <= toDate for item in data['date']])]
	fromDate, toDate = convertStrToDatetime(fromDate), convertStrToDatetime(toDate)
	currentDate = fromDate
	dayNumber = (toDate - fromDate).days + 1
	for day in range(dayNumber):
		globalSum.append(summer(data, id, currentDate))
		currentDate += datetime.timedelta(days=1)
	return globalSum

def initPandasDataFrame(matrix:list):
	data = {}
	past_columns = future_columns = []

	for item in range((toDate - fromDate).days + 1):
		past_columns.append("day_" + str(item))

	matrix = numpy.array(matrix).transpose()
	header = ["coffee", "water", "sugar", "milk"]

	for index, item in enumerate(matrix):
		data[header[index]] = item

	return pandas.DataFrame(data=data)

def createDataFrame(values:list):
	rawDataFrame = [ ]
	dictionary = {}

	past_columns = [f"past_{i}" for i in range(past)]
	future_columns = [f"future_{i}" for i in range(future)]
	
	for i in range(start, end):
		rawDataFrame.append(list(values[(i - past) : (i + future)]))

	dataFrame = pandas.DataFrame(rawDataFrame, columns=(past_columns + future_columns))

	dictionary['x'] = dataFrame[past_columns] [:-1]
	dictionary['y'] = dataFrame[future_columns] [:-1]
	dictionary['x_latest'] = dataFrame[past_columns] [-1:]
	dictionary['y_latest'] = dataFrame[future_columns] [-1:]

	return dataFrame, dictionary

fromDate, toDate = convertStrToDatetime(fromDate), convertStrToDatetime(toDate)
databaseCredits = json.load(open("DataBaseCredits.json"))
url = "mysql+pymysql://" + databaseCredits['userName'] + ":" + databaseCredits['pass'] + "@" + databaseCredits['host'] + "/" + "coffeeBreaker"
conn = create_engine(url)
data = pandas.read_sql("SELECT * FROM CoffeeBreakerDataTable", con=conn)
matrix = summerIdByDate(data, id, fromDate, toDate)

ingredients = [ [], [], [], [] ] # coffee, water, sugar, milk
end = len(matrix) - future

for row in matrix:
	for index in range(4):
		ingredients[index].append(row[index])

ingredientsNames = ("coffee", "water", "sugar", "milk")
dataFrameDict = {}
tableDict = {}

for index, item in enumerate(ingredientsNames):
	currentDataFrame, currentDictionary = createDataFrame(ingredients[index])
	dataFrameDict[item] = currentDataFrame
	tableDict[item] = currentDictionary

''' LINEAR REGRESSION '''
LinReg = LinearRegression()

def lenReg():
	for name in ingredientsNames:
		LinReg.fit(tableDict[name]['x'], tableDict[name]['y'])
		prediction = LinReg.predict(tableDict[name]['x_latest'])

		print(prediction)
		print(tableDict[name]['y_latest'])

lenReg()

''' Neural Networks '''
MLP = MLPRegressor(max_iter=500, hidden_layer_sizes=(100,100,100))

def mlpReg():
	for name in ingredientsNames: 
		MLP.fit(tableDict[name]['x'], tableDict[name]['y'])
		prediction = MLP.predict(tableDict[name]['x_latest']) # NN predict for coffee
		print(prediction)
		print(tableDict[name]['y_latest'])

''' RandomForest '''

RFR = RandomForestRegressor(n_estimators = 1000, max_depth=10)

def rfrReg():
	for name in ingredientsNames:
		RFR.fit(tableDict[name]['x'], tableDict[name]['y'].values.ravel())
		prediction = RFR.predict(tableDict[name]['x_latest']) #RFR predict for coffee
		print(prediction)
		print(tableDict[name]['y_latest'])

rfrReg()

''' NeighborsRegressor '''
KNN = KNeighborsRegressor(n_neighbors=3)

def knnReg():
	for name in ingredientsNames:
		KNN.fit(tableDict[name]['x'], tableDict[name]['y'])
		prediction = KNN.predict(tableDict[name]['x_latest']) #NR predict for coffee
		print(prediction)
		print(tableDict[name]['y_latest'])

''' MLPRegressor '''
MLP = MLPRegressor(hidden_layer_sizes=(100, 100, 100))
GSCV = GridSearchCV(MLP , {
    "max_iter": [100, 500, 1000],
    "learning_rate_init": [0.001, 0.01],
}, cv=3, scoring='neg_mean_absolute_error')

def mlpReg():
	for name in ingredientsNames:
		GSCV.fit(tableDict[name]['x'], tableDict[name]['y'])
		GSCV.best_score_
		model = GSCV.best_estimator_
		prediction = model.predict(tableDict[name]['x_latest'])[0] #MLP predict for coffee
		print(prediction)
		print(tableDict[name]['y_latest'])

mlpReg()
''' MLPRegressor '''

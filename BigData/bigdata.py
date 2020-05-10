import numpy
import pandas
import matplotlib.pyplot as plt
import pymysql
import datetime
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

def convertStrToDatetime(date):
	return datetime.date(int(date[0:4]), int(date[5:7]), int(date[8:10]))

def getCredits(path:str="databaseCredits.txt"):
	databaseCredits = []
	with open (path, "r") as creditsFile:
		for row in creditsFile:
			databaseCredits.append(row[:-1])
	return databaseCredits

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

fromDate, toDate = convertStrToDatetime(fromDate), convertStrToDatetime(toDate)
databaseCredits = getCredits()
url = "mysql+pymysql://" + databaseCredits[1] + ":" + databaseCredits[2] + "@" + databaseCredits[0] + "/" + "coffeeBreaker"
conn = create_engine(url)
data = pandas.read_sql("SELECT * FROM CoffeeBreakerDataTable", con=conn)
matrix = summerIdByDate(data, id, fromDate, toDate)

raw_df = [ ]
columnMilk = []
for row in matrix:
	columnMilk.append(row[1])
values = columnMilk

start = past = 7
future = 1
end = len(matrix) - future

for i in range(start, end):
	past_and_future_values = values [(i - past) : (i + future)]
	raw_df.append(list(past_and_future_values))

past_columns = [f"past_{i}" for i in range(past)]
future_columns = [f"future_{i}" for i in range(future)]

dataFrame = pandas.DataFrame(raw_df, columns=(past_columns + future_columns))

x = dataFrame[past_columns] [:-1]
y = dataFrame[future_columns] [:-1]

x_test = dataFrame[past_columns] [-1:]
y_test = dataFrame[future_columns] [-1:]

''' LINEAR REGRESSION '''
LinReg = LinearRegression()

LinReg.fit(x,y)
prediction = LinReg.predict(x_test)
plt.plot(prediction[0], label="Prediction")

plt.plot(y_test.iloc[0], label="Real")
plt.legend()

print(mean_absolute_error(prediction[0], y_test.iloc[0]))

"""
''' Neural Networks '''

MLP = MLPRegressor(max_iter=500, hidden_layer_sizes=(100,100,100))
MLP.fit(x, y)

prediction = MLP.predict(x_test)
plt.plot(prediction[0], label="Prediction")
plt.plot(y_test.iloc[0], label="Real")
plt.legend()

mean_absolute_error(prediction[0], y_test.iloc[0])


''' RandomForest '''

RFR = RandomForestRegressor(n_estimators = 1000, max_depth=10)
RFR.fit(x,y)

prediction = RFR.predict(x_test)
plt.plot(prediction[0], label="Prediction")
plt.plot(y_test.iloc[0], label="Real")
plt.legend()

mean_absolute_error(prediction[0], y_test.iloc[0])


''' NeighborsRegressor '''
KNN = KNeighborsRegressor(n_neighbors=1)
KNN.fit(x,y)

prediction = KNN.predict(x_test)
plt.plot(prediction[0], label="Prediction")
plt.plot(y_test.iloc[0], label="Real")
plt.legend()

mean_absolute_error(prediction[0], y_test.iloc[0])

''' MLPRegressor '''
MLP = MLPRegressor(hidden_layer_sizes=(100,100,100))
GSCV = GridSearchCV(MLP , {
    "max_iter": [100,500,1000],
    "learning_rate_init": [0.001, 0.01],
}, cv=3, scoring='neg_mean_absolute_error')

GSCV.fit(x, y)
GSCV.best_score_
model = GSCV.best_estimator_

prediction = model.predict(x_test)[0]
plt.plot(prediction, label="Prediction")
plt.plot(y_test.iloc[0], label="Real")
plt.legend()

mean_absolute_error(prediction, y_test.iloc[0])
"""

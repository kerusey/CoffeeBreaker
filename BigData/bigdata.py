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

def initPandasDataFrame(matrix):
	data = {}
	past_columns = future_columns = []

	for item in range((toDate - fromDate).days + 1):
		past_columns.append("day_" + str(item))

	matrix = numpy.array(matrix).transpose()
	header = ["coffee", "water", "sugar", "milk"]

	for index, item in enumerate(matrix):
		data[header[index]] = item

	return pandas.DataFrame(data=data)


fromDate, toDate = convertStrToDatetime(fromDate), convertStrToDatetime(toDate)
databaseCredits = getCredits()
url = "mysql+pymysql://" + databaseCredits[1] + ":" + databaseCredits[2] + "@" + databaseCredits[0] + "/" + "coffeeBreaker"
conn = create_engine(url)
data = pandas.read_sql("SELECT * FROM CoffeeBreakerDataTable", con=conn)
matrix = summerIdByDate(data, id, fromDate, toDate)

dataFrame = initPandasDataFrame(matrix)
print(dataFrame)

"""
column = [];        # empty list
for row in matrix:
  column.append(row[1])
values = matrix[0:31]
"""
columnCoffee = []; # empty list
for row in matrix:
	columnCoffee.append(row[0])
coffeeValues = columnCoffee

columnWater = []; # empty list
for row in matrix:
	columnWater.append(row[1])
waterValues = columnWater

columnSugar = []; # empty list
for row in matrix:
	columnSugar.append(row[2])
sugarValues = columnSugar

columnMilk = []; # empty list
for row in matrix:
	columnMilk.append(row[3])
milkValues = columnMilk

past = 7
future = 1

start = past
end = len(matrix) - future

print(start, end)

rawdf_coffee = [ ]
for i in range(start, end):
    past_and_future_coffee = coffeeValues [(i - past) : (i + future)]
    rawdf_coffee.append(list(past_and_future_coffee))

past_coffee = [f"past_{i}" for i in range(past)]
future_coffee = [f"future_{i}" for i in range(future)]

df_coffee = pandas.DataFrame(rawdf_coffee, columns=(past_coffee + future_coffee))
#print(df_coffee)

xc = df_coffee[past_coffee] [:-1]
yc = df_coffee[future_coffee] [:-1]

xc_test = df_coffee[past_coffee] [-1:]
yc_test = df_coffee[future_coffee] [-1:]

rawdf_water = [ ]
for i in range(start, end):
    past_and_future_water = waterValues [(i - past) : (i + future)]
    rawdf_water.append(list(past_and_future_water))

past_water = [f"past_{i}" for i in range(past)]
future_water = [f"future_{i}" for i in range(future)]

df_water = pandas.DataFrame(rawdf_water, columns=(past_water + future_water))
#print(df_water)

xw = df_water[past_water] [:-1]
yw = df_water[future_water] [:-1]

xw_test = df_water[past_water] [-1:]
yw_test = df_water[future_water] [-1:]

rawdf_sugar = [ ]
for i in range(start, end):
    past_and_future_sugar = sugarValues [(i - past) : (i + future)]
    rawdf_sugar.append(list(past_and_future_sugar))

past_sugar = [f"past_{i}" for i in range(past)]
future_sugar = [f"future_{i}" for i in range(future)]

df_sugar = pandas.DataFrame(rawdf_sugar, columns=(past_sugar + future_sugar))
#print(df_sugar)

xs = df_sugar[past_sugar] [:-1]
ys = df_sugar[future_sugar] [:-1]

xs_test = df_sugar[past_sugar] [-1:]
ys_test = df_sugar[future_sugar] [-1:]

rawdf_milk = [ ]
for i in range(start, end):
    past_and_future_milk = milkValues [(i - past) : (i + future)]
    rawdf_milk.append(list(past_and_future_milk))

past_milk = [f"past_{i}" for i in range(past)]
future_milk = [f"future_{i}" for i in range(future)]

df_milk = pandas.DataFrame(rawdf_milk, columns=(past_milk + future_milk))
#print(df_milk)

xm = df_milk[past_milk] [:-1]
ym = df_milk[future_milk] [:-1]

xm_test = df_milk[past_milk] [-1:]
ym_test = df_milk[future_milk] [-1:]


''' testing machine learning methods for coffee'''

''' LINEAR REGRESSION '''
LinReg = LinearRegression()

LinReg.fit(xc,yc) #LR predict for coffee
prediction = LinReg.predict(xc_test)
print(prediction)
print(yc_test)

"""
plt.plot(prediction, label="Prediction")
plt.plot(y_test.iloc, label="Real")
plt.legend()

mean_absolute_error(prediction, y_test.iloc)
print(mean_absolute_error)
"""
LinReg.fit(xw,yw) #LR predict for water
prediction = LinReg.predict(xw_test)
print(prediction)
print(yw_test)

LinReg.fit(xs,ys) #LR predict for sugar
prediction = LinReg.predict(xs_test)
print(prediction)
print(ys_test)

LinReg.fit(xm,ym) #LR predict for milk
prediction = LinReg.predict(xm_test)
print(prediction)
print(ym_test)

''' Neural Networks '''

MLP = MLPRegressor(max_iter=500, hidden_layer_sizes=(100,100,100))

MLP.fit(xc, yc)
prediction = MLP.predict(xc_test) #NN predict for coffee
print(prediction)
print(yc_test)
"""
plt.plot(prediction[0], label="Prediction")
plt.plot(y_test.iloc[0], label="Real")
plt.legend()

print(mean_absolute_error(y_test, prediction))"""

MLP.fit(xw, yw)
prediction = MLP.predict(xw_test) #NN predict for water
print(prediction)
print(yw_test)

MLP.fit(xs, ys)
prediction = MLP.predict(xs_test) #NN predict for sugar
print(prediction)
print(ys_test)

MLP.fit(xm, ym)
prediction = MLP.predict(xm_test) #NN predict for milk
print(prediction)
print(ym_test)

''' RandomForest '''

RFR = RandomForestRegressor(n_estimators = 1000, max_depth=10)

RFR.fit(xc,yc)
prediction = RFR.predict(xc_test) #RFR predict for coffee
print(prediction)
print(yc_test)

"""
plt.plot(prediction, label="Prediction")
plt.plot(y_test.iloc, label="Real")
plt.legend()

print(mean_absolute_error(y_test, prediction))
"""
RFR.fit(xw,yw)
prediction = RFR.predict(xw_test) #RFR predict for water
print(prediction)
print(yw_test)

RFR.fit(xs,ys)
prediction = RFR.predict(xs_test) #RFR predict for sugar
print(prediction)
print(ys_test)

RFR.fit(xm,ym)
prediction = RFR.predict(xm_test) #RFR predict for milk
print(prediction)
print(ym_test)


''' NeighborsRegressor '''
KNN = KNeighborsRegressor(n_neighbors=3)

KNN.fit(xc,yc)
prediction = KNN.predict(xc_test) #NR predict for coffee
print(prediction)
print(yc_test)

"""
plt.plot(prediction[0], label="Prediction")
plt.plot(y_test.iloc[0], label="Real")
plt.legend()

print(mean_absolute_error(y_test, prediction))
"""
KNN.fit(xw,yw)
prediction = KNN.predict(xw_test) #NR predict for water
print(prediction)
print(yw_test)

KNN.fit(xs,ys)
prediction = KNN.predict(xs_test) #NR predict for sugar
print(prediction)
print(ys_test)

KNN.fit(xm,ym)
prediction = KNN.predict(xm_test) #NR predict for milk
print(prediction)
print(ym_test)

''' MLPRegressor '''
MLP = MLPRegressor(hidden_layer_sizes=(100,100,100))
GSCV = GridSearchCV(MLP , {
    "max_iter": [100,500,1000],
    "learning_rate_init": [0.001, 0.01],
}, cv=3, scoring='neg_mean_absolute_error')

GSCV.fit(xc, yc)
GSCV.best_score_
model = GSCV.best_estimator_

prediction = model.predict(xc_test)[0] #MLP predict for coffee
print(prediction)
print(yc_test)

"""
plt.plot(prediction, label="Prediction")
plt.plot(y_test.iloc, label="Real")
plt.legend()

print(mean_absolute_error(y_test, prediction))
"""
GSCV.fit(xw, yw)
GSCV.best_score_
model = GSCV.best_estimator_

prediction = model.predict(xw_test)[0] #MLP predict for water
print(prediction)
print(yw_test)

GSCV.fit(xs, ys)
GSCV.best_score_
model = GSCV.best_estimator_

prediction = model.predict(xs_test)[0] #MLP predict for sugar
print(prediction)
print(ys_test)

GSCV.fit(xm, ym)
GSCV.best_score_
model = GSCV.best_estimator_

prediction = model.predict(xm_test)[0] #MLP predict for mikl
print(prediction)
print(ym_test)

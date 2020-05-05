import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from sklearn.linear_model import LinearRegression
from sklearn.metrics import mean_absolute_error
from sklearn.neural_network import MLPRegressor
from sklearn.ensemble import RandomForestRegressor
from sklearn.neighbors import KNeighborsRegressor
from sklearn.model_selection import GridSearchCV


data = pd.read_csv('E:/Рабочий стол/bd5.csv')

data.sort_values(by=['date'],
        ascending=[True]).head()

values = data.milk
past = 7 * 4
future = 3

start = past
end = len(values) - future
print(start, end)

raw_df = [ ]
for i in range(start, end):
    past_and_future_values = values [(i - past) : (i + future)]
    raw_df.append(list(past_and_future_values))

past_columns = [f"past_{i}" for i in range(past)]
future_columns = [f"future_{i}" for i in range(future)]

df = pd.DataFrame(raw_df, columns=(past_columns + future_columns))
df

x = df[past_columns] [:-1]
y = df[future_columns] [:-1]

x_test = df[past_columns] [-1:]
y_test = df[future_columns] [-1:]

''' LINEAR REGRESSION '''
LinReg = LinearRegression()

LinReg.fit(x,y)
prediction = LinReg.predict(x_test)
prediction
y_test
plt.plot(prediction[0], label="Prediction")

plt.plot(y_test.iloc[0], label="Real")
plt.legend()

mean_absolute_error(prediction[0], y_test.iloc[0])


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

import csv

amountOfDates = ["2020-01-02", "2020-01-01", "2020-01-08", "2020-01-10", "2020-01-15"]
machineId = 7

def getAmountOfDays(dates:list, id:int):
	globalResult = [0]*4
	for day in dates:
		result = simpify(day, id)
		for i in range(len(result)):
			globalResult[i] += result[i]
	return globalResult

def simpify(currentData:str, id:int):
	sumMilk = sumCoffee = sumSugar = sumWater = 0
	with open('database.csv') as csvfile:
		data = csv.reader(csvfile)

		for i, row in enumerate(data):
			if(row[1] == currentData):
				break

		for i, row in enumerate(data):
			if (i == 0):
				continue
			else:
				if(int(row[0]) == id):
					sumMilk += int(row[3])
					sumSugar += int(row[4])
					sumCoffee += int(row[5])
					sumWater += int(row[6])
				
				if (currentData != row[1]):
					break;
		result = [sumMilk, sumCoffee, sumWater, sumSugar]
	return result

# print(getAmountOfDays(amountOfDates, machineId))

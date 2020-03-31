import csv

currentData = "2020-01-01"

def simpify():
	count = sumMilk = sumCoffee = sumSugar = sumWater = 0

	with open('database.csv') as csvfile:
		data = csv.reader(csvfile)
		for row in data:
			count += 1
			if(count == 1):
				continue
			else:
				sumMilk += int(row[2])
				sumSugar += int(row[3])
				sumCoffee += int(row[4])
				sumWater += int(row[5])
				if (currentData != row[0]):
					break;
		result = {sumMilk, sumCoffee, sumWater, sumSugar}
	return result

# print(simpify())

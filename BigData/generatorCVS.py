import csv
import random

days = 31
orders = 100

def randomTime():
	return str(random.randint(0, 23)) + ":" + str(random.randint(0, 59))

with open('database.csv', 'w', newline='') as csvfile:
	fieldnames = ['date', 'time', 'milk', 'sugar', 'coffee', 'whater']
	writer = csv.DictWriter(csvfile, fieldnames=fieldnames)
	writer.writeheader()

	for j in range(days):
		for i in range(orders):
			if(j + 1 > 9):
				writer.writerow({'date': '2020-01-' + str(j+1), 'time': randomTime(), 'milk': random.randint(0, 2), 'sugar': random.randint(0, 4), 'coffee': random.randint(0, 4), 'whater': random.randint(0, 4)})
			else:
				writer.writerow({'date': '2020-01-0' + str(j+1), 'time': randomTime(), 'milk': random.randint(0, 2), 'sugar': random.randint(0, 4), 'coffee': random.randint(0, 4), 'whater': random.randint(0, 4)})

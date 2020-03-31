import csv
import random

days = 31
orders = 100

def randomTime():
	hrs = str(random.randint(0, 23))
	mins = str(random.randint(0, 59))
	if(int(mins) < 10):
		mins = "0" + mins
	if(int(hrs) < 10):
		hrs = "0" + hrs
	return hrs + ":" + mins

with open('database.csv', 'w', newline='') as csvfile:
	fieldnames = ['date', 'time', 'milk', 'sugar', 'coffee', 'water']
	writer = csv.DictWriter(csvfile, fieldnames=fieldnames)
	writer.writeheader()

	for j in range(days):
		for i in range(orders):
			if(j + 1 > 9):
				writer.writerow({'date': '2020-01-' + str(j+1), 'time': randomTime(), 'milk': random.randint(0, 1), 'sugar': random.randint(0, 4), 'coffee': random.randint(1, 4), 'water': random.randint(1, 4)})
			else:
				writer.writerow({'date': '2020-01-0' + str(j+1), 'time': randomTime(), 'milk': random.randint(0, 1), 'sugar': random.randint(0, 4), 'coffee': random.randint(1, 4), 'water': random.randint(1, 4)})

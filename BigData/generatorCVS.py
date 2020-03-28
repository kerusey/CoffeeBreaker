import csv
import random

def randomTime():
	return str(random.randint(0, 23)) + ":" + str(random.randint(0, 59))

with open('database.csv', 'w', newline='') as csvfile:
	fieldnames = ['date', 'time', 'milk', 'sugar']
	writer = csv.DictWriter(csvfile, fieldnames=fieldnames)
	writer.writeheader()

	for j in [1, 2, 3, 4, 5]:
		for i in range(5):
			writer.writerow({'date': '2020-01-0' + str(j), 'time': randomTime(), 'milk': random.randint(0, 2), 'sugar': random.randint(0, 4),})
      

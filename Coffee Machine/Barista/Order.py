# FTP folder home/pi/FTP/order
import json
import os

folderPath = "/home/pi/FTP/order/"
fileName = "order.json"

def getOrder(): 
	with open(folderPath + fileName) as json_file:
		json_data = json.load(json_file)
	coffeeType = json_data['type']
	strenght = json_data['strenght']
	volume = json_data['volume']
	milk = json_data['milk']
	chokolate = json_data['chokolate']
	canella = json_data['canella']
	shugar = json_data['shugar']
	os.remove(folderPath + fileName)
	return coffeeType, strenght, volume, milk, chokolate, canella, shugar # OK

# tuple = getOrder()

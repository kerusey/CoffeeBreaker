import json
import os
import sys
Request = sys.path.insert(1, '/home/pi/CoffeeBreaker/CoffeeMachine/Request.py')
import Request

with open("/home/pi/Documents/MachineSettings.json") as json_file:
        MachineSettings = json.load(json_file)

folderPath = MachineSettings['LogsPath']
fileName = "Order" + str(MachineSettings['MachineID']) + ".json"

def getOrder():
    with open(folderPath + fileName) as json_file:
        json_data = json.load(json_file)
    #
    coffeeType = json_data['coffeeType']
    strenght = json_data['strength']
    volume = json_data['volume']
    milk = json_data['milk']
    shugar = json_data['sugar']
    # coffee settings block
    os.remove(folderPath + fileName)
    return coffeeType, strenght, volume, milk, chokolate, canella, shugar # OK

# tuple = getOrder()

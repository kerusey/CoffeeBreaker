#from .CoffeePudding import Barista

from flask import Flask
from flask_restful import Resource, Api, reqparse
import netifaces
import json
import requests
import os
from CoffeePudding import Barista

def getLan(): # OK
	interfaces = netifaces.interfaces()
	for i in interfaces:
		if (i == 'lo'):
			continue
		iface = netifaces.ifaddresses(i).get(netifaces.AF_INET)
		if (iface != None):
			for j in iface:
				return str(j['addr'])

host = getLan()
port = 8090

ingredients = ( "coffeeType", "milk", "strength", "sugar", "volume" )
dataBaseHeader = ( "coffeeID", "water", "sugar", "milk" )

scriptPath = os.path.dirname(os.path.abspath(__file__)) + "/"

MachineSettingsPath = scriptPath + "MachineSettings.json"
coffeeMachinePoolPath = scriptPath + "CoffeeMachinePool.json"

with open(MachineSettingsPath) as jsonFile:
	MachineSettings = json.load(jsonFile)

with open(coffeeMachinePoolPath) as jsonFile:
	coffeeMachinePool = json.load(jsonFile)

def postDataToDataBase(coffeeID:int, order:dict):
	order["coffeeID"] = coffeeID
	del order["strength"]
	del order["coffeeType"]
	requests.post("http://chaos.fstudio.space:8090/ToDataBase", json=json.dumps(order))

def chooseCoffeeMachine(coffeeMachinePool:dict=coffeeMachinePool): # returns ID of available coffeeMachine from  coffeeMachinePool
	for item in coffeeMachinePool:
		if(coffeeMachinePool[item]['status'] == "free"):
			coffeeMachinePool[item]['status'] = "active"
			with open (coffeeMachinePoolPath, 'w') as jsonFile:
				json.dump(coffeeMachinePool, jsonFile, indent=4)
			return item
	return None

def freeCoffeeMachine(coffeeMachineID:int):
	coffeeMachinePool[str(coffeeMachineID)]['status'] = "free"
	json.dump(coffeeMachinePool, open(coffeeMachinePoolPath, 'w'), indent=4)

path = os.path.dirname(os.path.abspath(__file__))
app = Flask(__name__)
api = Api(app)

class OrderToCluster(Resource):
	def post(self):
		parser = reqparse.RequestParser()
		parser.add_argument("coffeeType", type=str, location='json')
		for item in ingredients:
			if (item == "coffeeType"):
				continue
			parser.add_argument(item, type=int, location='json')
		order = parser.parse_args()
		currentMachine = chooseCoffeeMachine()
		if (currentMachine is None):
			return 'CBERR###' # all coffee machines are in use
		else:
			Barista.makeOrder(machine=currentMachine, order=order)
			postDataToDataBase(currentMachine, order) # TODO request some time user to wait
			time.sleep(1200) # TODO set timer of making order
			freeCoffeeMachine(currentMachine)
			return currentMachine # returns the id of coffee machine that assigned to current order 

class Ping(Resource):
	def get(self):
		return 200

api.add_resource(OrderToCluster, "/post/OrderToCluster")
api.add_resource(Ping, "/ping")
app.run(host=host, port=port, debug=False, threaded=True)

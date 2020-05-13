from flask import Flask
from flask_restful import Resource, Api, reqparse
import netifaces
import json
import requests
import os

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
with open(MachineSettingsPath) as json_file:
	MachineSettings = json.load(json_file)

def postDataToDataBase(coffeeID:int, order:dict):
	order["coffeeID"] = coffeeID
	requests.post("http://chaos.fstudio.space:8090/ToDataBase", json=json.dumps(order))

def chooseCoffeeMachine(coffeeMachinePool:dict=coffeeMachinePool): # returns ID of available coffeeMachine from  coffeeMachinePool
	return 0 # TODO realization of this method

path = os.path.dirname(os.path.abspath(__file__))
app = Flask(__name__)
api = Api(app)

class OrderToCluster(Resource):
	def post(self):
		parser = reqparse.RequestParser()
		parser.add_argument("coffeeType", type=str, location='json')
		for item in ingredients:
			if (item is "coffeeType"):
				continue
			parser.add_argument(item, type=int, location='json')
		order = parser.parse_args()
		currentMachine = chooseCoffeeMachine()
		barista.makeOrder(currentMachine, order)
		postDataToDataBase(currentMachine, order)
		return 200

api.add_resource(OrderToCluster, "/post/OrderToCluster")
app.run(host=host, port=port, debug=True, threaded=True)

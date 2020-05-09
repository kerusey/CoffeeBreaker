from flask import Flask
from flask_restful import Resource, Api
import netifaces
import os
import json

def getLan(): # OK
	interfaces = netifaces.interfaces()
	for i in interfaces:
		if (i == 'lo'):
			continue
		iface = netifaces.ifaddresses(i).get(netifaces.AF_INET)
		if (iface != None):
			for j in iface:
				return str(j['addr'])

coffeeMachinePool = {}
with open("CoffeeAmount.json") as jsonFile:
	data = json.load(jsonFile)
	for row in data:
		coffeeMachinePool[row] = data[row]

host = getLan()
port = 8090
path = os.path.dirname(os.path.abspath(__file__))
app = Flask(__name__)
api = Api(app)

class ConnectionTest(Resource):
	def get(self):
		print("connection established")
		return {'connection': 'established'}

class AnotherConnectionTest(Resource):
	def get(self):
		print("hello, world")
		return "hello, world"

api.add_resource(ConnectionTest, '/connectionTest')
api.add_resource(AnotherConnectionTest, '/fuckup')
app.run(host=host, port=port, debug=True)

from flask import Flask
from flask_restful import Resource, Api, reqparse
import netifaces
import os
import json
import requests
import threading
import DataBaseInsertion

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
path = os.path.dirname(os.path.abspath(__file__))+"/"
app = Flask(__name__)
api = Api(app)
coffeeMachineClusterPool = {}

coffeeShopInformation={}

with open(path+"CoffeeMachineClusterPool.json") as jsonFile:
    data = json.load(jsonFile)
    for row in data:
        coffeeMachineClusterPool[row] = data[row]

ingredients = ( "coffeeType", "milk", "strength", "sugar", "volume" )
dataBaseHeader = ( "coffeeID", "water", "sugar", "milk" )

class OrderFromApp(Resource):
    def post(self, id):
        parser = reqparse.RequestParser()
        parser.add_argument("coffeeType", type=str, location='json')
        for item in ingredients:
            if (item == "coffeeType"):
                continue
            parser.add_argument(item, type=int, location='json')
        args = parser.parse_args()
        requests.post('http://' + str(coffeeMachineClusterPool[id]) + ":" + str(port) + "/ToCluster", json=json.dumps(args))
        return 200

class DataToDataBase(Resource):
    def post(self):
        parser = reqparse.RequestParser()
        for name in dataBaseHeader:
            parser.add_argument(name, type=int, location='json')
        args = parser.parse_args()
        DataBaseInsertion.valueInsertion(args)
        DataBaseInsertion.printingBD()
        return 200

class CoffeeHouses(Resource):
    def get(self):
        with open(path+"CoffeeHouses.json","r") as cafesRead:
            info = json.load(cafesRead)
            for row in info:
                coffeeShopInformation[row] = info[row]
        return 200

api.add_resource(OrderFromApp, "/post/OrderFromApp_<id>")
api.add_resource(DataToDataBase, "/post/ToDataBase")
api.add_resource(CoffeeHouses, "/CoffeeHouses")
app.run(host, port, debug=True,threaded=True)

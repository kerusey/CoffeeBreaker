from flask import Flask
from flask_restful import Resource, Api, reqparse
import netifaces
import os
import json
import requests

def getLan(): # OK
    interfaces = netifaces.interfaces()
    for i in interfaces:
        if (i == 'lo'):
            continue
        iface = netifaces.ifaddresses(i).get(netifaces.AF_INET)
        if (iface != None):
            for j in iface:
                return str(j['addr'])

coffeeMachineClusterPool = {}
with open("CoffeeMachineClusterPool.json") as jsonFile:
    data = json.load(jsonFile)
    for row in data:
        coffeeMachineClusterPool[row] = data[row]

host = getLan()
port = 8090
path = os.path.dirname(os.path.abspath(__file__))
app = Flask(__name__)
api = Api(app)

ingredients = ( "coffeeType", "milk", "strength", "sugar", "volume" )
dataBaseHeader = ( "coffeeID", "water", "sugar", "milk" )

class OrderFromApp(Resource):
    def post(self, id):
        parser = reqparse.RequestParser()
        parser.add_argument("coffeeType", type=str, location='json')
        for item in ingredients:
            if (item is "coffeeType"):
                continue
            parser.add_argument(item, type=int, location='json')
        args = parser.parse_args()
        requests.post('http://' + str(coffeeMachineClusterPool[id]) + ":" + str(port) + "/ToCluster_<id>", json=json.dumps(args))
        return 200

class DataToDataBase(Resource):
    def get(self, id):
        parser = reqparse.RequestParser()
        for name in dataBaseHeader:
            parser.add_argument(name, type=int, location='json')
        args = parser.parse_args()
        return 200

api.add_resource(OrderFromApp, "/post/ToServerFromApp_<id>")
api.add_resource(DataToDataBase, "/post/ToDataBase_<id>")


app.run(host=host, port=port, debug=True)
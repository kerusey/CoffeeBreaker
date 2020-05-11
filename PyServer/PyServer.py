from flask import Flask
from flask_restful import Resource, Api, reqparse
import netifaces
import os
import json
import requests

ingredients = ("coffeeType", "milk", "strength", "sugar", "volume")

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

class OrderFromApp(Resource):
    def post(self, id):
        parser = reqparse.RequestParser()
        parser.add_argument("coffeeType", type=str, location='json')
        for item in ingredients:
            if (item == "coffeeType"):
                continue
            parser.add_argument(item, type=int, location='json')
        args = parser.parse_args()
        requests.post('http://' + str(coffeeMachineClusterPool[id]) + ":" + str(port) + "/post/ToCluster_<id>", json=json.dumps(args))

ingredientsForBd = ("milk", "sugar","water","ID")
class OrderRaspberry(Resource):
    def get(self, id):
        parser = reqparse.RequestParser()
        parser.add_argument('ID', type=str, location='json')
        for item in ingredientsForBd:
            if (item=="ID"):
                continue
            parser.add_argument(item, type=int, location='json')
        args = parser.parse_args()
        return args

api.add_resource(OrderFromApp, '/post/OrderFromApp_<id>')
api.add_resource(OrderRaspberry, '/post/OrderFromRaspberry_<id>')
app.run(host=host, port=port, debug=True)
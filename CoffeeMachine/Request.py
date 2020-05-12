from flask import Flask
from flask_restful import Resource, Api, reqparse
import netifaces
import json
import requests
import os

globalQueue = []
ingredients = ( "coffeeType", "milk", "strength", "sugar", "volume" )
dataBaseHeader = ( "coffeeID", "water", "sugar", "milk" )

scriptPath = os.path.dirname(os.path.abspath(__file__)) + "/"

MachineSettingsPath = scriptPath + "MachineSettings.json"
with open(MachineSettingsPath) as json_file:
    MachineSettings = json.load(json_file)

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
with open("CoffeeMachinePool.json") as jsonFile:
    data = json.load(jsonFile)
    for row in data:
        coffeeMachinePool[row] = data[row]

host = getLan()
port = 8090
path = os.path.dirname(os.path.abspath(__file__))
app = Flask(__name__)
api = Api(app)

class OrderToCluster(Resource):
    def post(self, id):
        parser = reqparse.RequestParser()
        parser.add_argument("coffeeType", type=str, location='json')
        for item in ingredients:
            if (item is "coffeeType"):
                continue
            parser.add_argument(item, type=int, location='json')
        args = parser.parse_args()
        globalQueue.append(args)
        return 200

def postDataToDataBase(coffeeID:int, dictionary:dict):
    requests.post("http://chaos.fstudio.space:8090/ToDataBase_" + coffeeID, json=json.dumps(dictionary))


api.add_resource(OrderToCluster, "/post/ToCluster_<id>")

app.run(host=host, port=port, debug=True)

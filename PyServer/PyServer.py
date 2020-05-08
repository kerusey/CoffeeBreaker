from flask import Flask
from flask_restful import Resource, Api
import netifaces
import os

def getLan(): # OK
	interfaces = netifaces.interfaces()
	for i in interfaces:
		if i == 'lo':
			continue
		iface = netifaces.ifaddresses(i).get(netifaces.AF_INET)
		if iface != None:
			for j in iface:
				return str(j['addr'])

host = getLan()
print(host)
port = 8090
path = os.path.dirname(os.path.abspath(__file__))

app = Flask(__name__)
api = Api(app)

class HelloWorld(Resource):
	def get(self):
		return {'hello': 'world'}

api.add_resource(HelloWorld, '/')

if __name__ == '__main__':
	app.run(host=host, port=port, debug=True)

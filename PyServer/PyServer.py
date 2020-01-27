from flask import Flask, request, jsonify
import requests
import json
import os.path 

app = Flask(__name__)

myHost = "172.16.0.37"
myPort = 8090

@app.route("/name")
def hello():
	print("val")
	return "Hello World!"

@app.route('/postOrder', methods = ['POST'])
def postJsonOrder():
	content = request.get_json()
	jjson = { "MachineID": str(content['MachineID']),
			"type": str(content['type']),
			"strenght": int(content['strenght']),
			"volume": int(content['volume']),
			"milk": bool(content['milk']),
			"chokolate": bool(content['chokolate']),
			"canella": bool(content['canella']),
			"shugar": int(content['shugar'])
		}
	js = json.dumps(jjson, sort_keys=True, indent=4, separators=(',', ': '))
	with open ('Order.json', 'w+') as f:
		
		f.write(js) 
	return "#¯\\_(ツ)_/¯#"   # OK

@app.route('/postToken', methods = ['POST'])
def postJsonToken():
	content = request.get_json()
	jjson = {'token': content['token']}
	js = json.dumps(jjson, sort_keys=True, indent=4, separators=(',', ': '))
	with open ('Token.json', 'w+') as f:
		f.write(js) 
	return "#¯\\_(ツ)_/¯#"   # OK

@app.route('/getToken/<id>')
def getJsonToken(id):
	path = "validations/"
	filename = 'Validate' + str(id) + '.json'
	if (os.path.isfile(path + filename)):
		with open(path + filename) as json_data:
			jsonToken = json.load(json_data)
		os.remove(path + filename)
		return jsonify(jsonToken)
	else: 
		return "0"

@app.route('/getOrder/<id>')
def getJsonOrder(id):
	path = "orders/"
	filename = 'Order' + str(id) + '.json'
	if(os.path.isfile(path + filename)):
		with open(path + filename) as json_data:
			jsonToken = json.load(json_data)
		os.remove(path + filename)
		return jsonify(jsonToken)
	else:
		return "0"


if __name__ == '__main__':
	app.run(host=myHost, port=myPort)

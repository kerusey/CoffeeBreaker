from flask import Flask, request, jsonify
import requests
import json
import os.path 

def getExistance(path, filename):
	if(os.path.isfile(path + filename)):
		with open(path + filename) as json_data:
			jsonFile = json.load(json_data)
		os.remove(path + filename)
		return jsonify(jsonFile)
	else:
		return "0"  #  OK

app = Flask(__name__)

myHost = "192.168.0.173"
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

@app.route('/postValidationStatus/<id>', methods = ['POST'])
def postValidationStatus(id):
	content = request.args()
	pass # FIXME

@app.route('/postOrderStatus/<id>', methods = ['POST'])
def postOrderStatus(id):
	pass

@app.route('/getToken/<id>')
def getJsonToken(id):
	path = "Validations/"
	filename = 'Validate' + str(id) + '.json'
	if(os.path.isfile(path + filename)):
		with open(path + filename) as json_data:
			jsonFile = json.load(json_data)
		os.remove(path + filename)
		return jsonify(jsonFile)
	else:
		return "0"  #  OK

@app.route('/getOrder/<id>')
def getJsonOrder(id):
	path = "Orders/"
	filename = 'Order' + str(id) + '.json'
	if(os.path.isfile(path + filename)):
		with open(path + filename) as json_data:
			jsonFile = json.load(json_data)
		os.remove(path + filename)
		return jsonify(jsonFile)
	else:
		return "0"  #  OK

'''
@app.route('/getValidationStatus/<id>')
def getValidationStatus(id):
	path = "ValidationStatuses/"
	filename = 'ValidationStatus' + str(id) + '.json'
	if(os.path.isfile(path + filename)):
		with open(path + filename) as json_data:
			jsonFile = json.load(json_data)
		os.remove(path + filename)
		return jsonify(jsonFile)
	else:
		return "0"  #  OK

@app.route('/getOrderStatus/<id>')
def getValidationStatus(id):
	path = "OrderStatuses/"
	filename = 'OrderStatus' + str(id) + '.json'
	if(os.path.isfile(path + filename)):
		with open(path + filename) as json_data:
			jsonFile = json.load(json_data)
		os.remove(path + filename)
		return jsonify(jsonFile)
	else:
		return "0"  #  OK
'''

if __name__ == '__main__':
	app.run(host=myHost, port=myPort)

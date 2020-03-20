from flask import Flask, request, jsonify
import requests
import json
import os.path

def getExistance(fullFileName):
	if(os.path.isfile(fullFileName)):
		with open(fullFileName) as json_data:
			jsonFile = json.load(json_data)
		return jsonFile
	else:
		return "0"  #  OK

def dumping(jjson, fullFileName):
	js = json.dumps(jjson, sort_keys=True, indent=4, separators=(',', ': '))
	with open (fullFileName + '.json', 'w+') as f:
		f.write(js)
	return "#" # OK

app = Flask(__name__)

myHost = "192.168.0.102"
myPort = 8090
myPath = os.path.dirname(os.path.abspath(__file__))

@app.route("/")
def hello():
	print("val")
	return "Hello World!"

@app.route('/postOrder/<id>', methods = ['POST'])
def postJsonOrder(id):
	path = myPath + "/Orders/"
	filename = "Order" + str(id)
	content = request.get_json()

	jjson ={"MachineID": int(content['MachineID']),
			"type": str(content['type']),
			"strenght": int(content['strenght']),
			"volume": int(content['volume']),
			"milk": bool(content['milk']),
			"shugar": int(content['shugar'])
			}

	return dumping(jjson, path + filename)

@app.route('/postToken/<id>', methods = ['POST'])
def postJsonToken(id):
	path = myPath + "/Tokens/"
	filename = "Token" + str(id)
	content = request.get_json()

	jjson = {'token': content['token']}

	return dumping(jjson, path + filename)


@app.route('/postTokenStatus/<id>', methods = ['POST'])
def postTokenStatus(id):
	path = myPath + "/TokenStatuses/"
	filename = "TokenStatus" + str(id)
	content = request.get_json()

	jjson = {'status':content['status']}

	return dumping(jjson, path + filename)

@app.route('/postOrderStatus/<id>', methods = ['POST'])
def postOrderStatus(id):
	path = myPath + "/OrderStatuses/"
	filename = "OrderStatus" + str(id)
	content = request.get_json()

	jjson = {'status': content['status']}

	return dumping(jjson, path + filename)

@app.route('/getToken/<id>') #  OK
def getJsonToken(id):
	path = myPath + "/Tokens/"
	filename = 'Token' + str(id) + '.json'

	responce = jsonify(getExistance(path + filename))
	os.remove(path + filename)
	return responce

@app.route('/getOrder/<id>') #  OK
def getJsonOrder(id):
	path = myPath + "/Orders/"
	filename = 'Order' + str(id) + '.json'

	responce = jsonify(getExistance(path + filename))
	os.remove(path + filename)
	return responce

@app.route('/getTokenStatus/<id>') #  OK
def getTokenStatus(id):
	path = myPath + "/TokenStatuses/"
	filename = 'TokenStatus' + str(id) + '.json'
	jsonFile = getExistance(path + filename)
	return "0" if (jsonFile == 0) else jsonFile

@app.route('/getOrderStatus/<id>') #  OK
def getOrderStatus(id):
	path = myPath + "/OrderStatuses/"
	filename = 'OrderStatus' + str(id) + '.json'
	jsonFile = getExistance(path + filename)
	return "0" if (jsonFile == "0")	else jsonFile

if __name__ == '__main__':
	app.run(host=myHost, port=myPort)

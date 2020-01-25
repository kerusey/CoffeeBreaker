from flask import Flask, request
import json
app = Flask(__name__)


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

@app.route('/getToken')
def getJsonToken():
	SITE_ROOT = os.path.realpath(os.path.dirname(__file__))
    json_url = os.path.join(SITE_ROOT,'static', 'test.json')
    token = open(json_url)
    stored_json = token.readlines()
	token.close()
	return stored_json # FIXME

if __name__ == '__main__':
	app.run(host="172.16.0.37", port=8090)

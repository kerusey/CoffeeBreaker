from flask import Flask, request

app = Flask(__name__)


@app.route("/")
def hello():
	return "Hello World!"

@app.route('/postjson', methods = ['POST'])
def postJsonHandler():
	print (request.is_json)
	content = request.get_json()
	print(content['device'])
	print(content['value'])
	print(content['timestamp'])
	return 'JSON posted'

if __name__ == '__main__':
	app.run(host="172.16.0.37", port=8090)


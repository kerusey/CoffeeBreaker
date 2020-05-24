from django.http import JsonResponse, HttpResponse
import os
import json
from django.views.decorators.csrf import csrf_exempt
import requests
#from DataBaseInsertion import valueInsertion, printingBD
from . import DataBaseInsertion

path = os.path.dirname(os.path.dirname(os.path.abspath(__file__))) + "/Globals/"

coffeeMachineClusterPool={}

with open(path + "CoffeeMachineClusterPool.json") as jsonFile:
		data = json.load(jsonFile)
		for row in data:
			coffeeMachineClusterPool[row] = data[row]

def ping(id):
	try:
		requests.get('http://' + str(coffeeMachineClusterPool[str(id)]) + ":8090/ping", timeout=5)
		return True
	except:
		return False

def getCoffeeHouses(request):
	coffeeHouses = {}
	with open(path + "CoffeeHouses.json") as jsonFile:
		data = json.load(jsonFile)
		for row in data:
			coffeeHouses[row] = data[row]
	return JsonResponse(coffeeHouses)

@csrf_exempt
def postDataToDataBase(request):
	data = json.loads(request.body)
	DataBaseInsertion.valueInsertion(data)
	DataBaseInsertion.printingBD()
	return HttpResponse(200)

@csrf_exempt
def	postOrderFromApp(request,id):
	data = json.loads(request.body)
	if (ping(id)):
		requests.post('http://' + str(coffeeMachineClusterPool[str(id)]) + ":8090/ToCluster", json=json.dumps(data))
		return HttpResponse(200)
	else:
		return HttpResponse('Error on cluster server side')
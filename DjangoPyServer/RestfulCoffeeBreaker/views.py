from django.http import JsonResponse, HttpResponse
from django.views.decorators.csrf import csrf_exempt
from django.conf import settings
import os
import json
import requests
from . import DataBaseInsertion

def ping(id):
	try:
		requests.get('http://' + str(settings.COFFEE_MACHINE_CLUSTER_POOL[str(id)]) + ":8090/ping", timeout=5)
		return True
	except:
		return False

def getCoffeeHouses(request):
	return JsonResponse(settings.COFFEE_HOUSES)

@csrf_exempt
def postDataToDataBase(request):
	data = json.loads(request.body)
	DataBaseInsertion.valueInsertion(data)
	# DataBaseInsertion.printingBD() # OPTIONAL THINGY
	return HttpResponse(200)

@csrf_exempt
def	postOrderFromApp(request,id):
	data = json.loads(request.body)
	if (ping(id)):
		requests.post('http://' + str(settings.COFFEE_MACHINE_CLUSTER_POOL[str(id)]) + ":8090/ToCluster", json=json.dumps(data))
		return HttpResponse(200)
	else:
		return HttpResponse('Error on cluster server side')

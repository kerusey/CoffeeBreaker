from django.http import JsonResponse, HttpResponse
from django.views.decorators.csrf import csrf_exempt
from django.conf import settings
from django.template import loader
import json
import requests
from . import DataBaseInsertion

def ping(coffeeClusterID):
	try:
		requests.get('http://' + str(settings.COFFEE_MACHINE_CLUSTER_POOL[str(coffeeClusterID)]) + ":8090/ping/", timeout=5)
		return True
	except Exception:
		return False

def getCoffeeHouses(request, typeof):
	accessableTypes = ("js", "json", "json-as-dict", "json-as-list", "number")
	numericTypes = ("number")
	
	if(typeof in accessableTypes):
		if(typeof in numericTypes):
			return HttpResponse(DataBaseInsertion.getDataConvertedToJson(typeof))
		else:
			return JsonResponse(DataBaseInsertion.getDataConvertedToJson(typeof))

	page_404 = loader.get_template(settings.TEMPLATE_SOURCE_DIR + "404.html")
	return HttpResponse(page_404.render())


@csrf_exempt
def postDataToDataBase(request):
	data = json.loads(request.body)
	DataBaseInsertion.valueInsertion(data)
	# DataBaseInsertion.printingBD() # OPTIONAL THINGY
	return HttpResponse(200)

@csrf_exempt
def	postOrderFromApp(request, coffeeClusterID):
	data = json.loads(request.body)
	if (ping(coffeeClusterID)):
		CoffeeMachineID = requests.post('http://' + str(settings.COFFEE_MACHINE_CLUSTER_POOL[str(coffeeClusterID)]) + ":8090/ToCluster", json=json.dumps(data))
		if (CoffeeMachineID == 404):
			return HttpResponse('CBERR###') # cannot reach ToCluster method on cluster side
		return HttpResponse(CoffeeMachineID) if CoffeeMachineID != 500 else HttpResponse('CBERR###') # 500 error to the cluster
	else:
		return HttpResponse('CBERR###') # cannot ping coffeeCluster (cluster is not responding)

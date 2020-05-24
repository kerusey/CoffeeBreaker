from django.http import JsonResponse
import os
import json

ingredients = ( "coffeeType", "milk", "strength", "sugar", "volume" )
dataBaseHeader = ( "coffeeID", "water", "sugar", "milk" )

path = os.path.dirname(os.path.dirname(os.path.abspath(__file__))) + "/Globals/"

def getCoffeeHouses(request):
	coffeeMachineClusterPool = {}
	with open(path + "CoffeeHouses.json") as jsonFile:
		data = json.load(jsonFile)
		for row in data:
			coffeeMachineClusterPool[row] = data[row]
	return JsonResponse(coffeeMachineClusterPool)
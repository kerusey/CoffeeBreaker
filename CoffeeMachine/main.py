from Validation import Visualizer
from CoffeePudding import Barista
import Request
import time
import os

def validateToken(token):
	if (Request.getToken() != token):
		return "FAILED"
	else:
		return "OK"

def scanActivity():
	currentToken = Visualizer.visualNewSession()
	Visualizer.stopSession()
	Request.postTokenStatus("READYTOSCAN")

	timer = 2
	while(Request.getTokenStatus() == "READYTOSCAN"):
		time.sleep(timer)
		timer += 2
		if (timer >= 90):
			timer = 2

	validationStatus = validateToken(currentToken)
	Request.postTokenStatus(validationStatus)
	if(validationStatus == "FAILED"):
		Visualizer.stopSession()
		return currentToken, True

	return currentToken, False

def finalOrder():
	Request.postOrderStatus("WAITING")

	timer = 2
	while(Request.getOrderStatus() == "WAITING"):
		time.sleep(timer)
		timer += 2
		if (timer >= 90):
			timer = 1      # OK

	return Request.getOrder()

def main():
	flag = True
	while flag:
		generalToken, flag = scanActivity()
	jsonOrder = finalOrder()
	print(jsonOrder)

	Request.postBd(jsonOrder, generalToken)
	# Barista.make(jsonOrder)
	Visualizer.stopSession()

while(True):
	main()

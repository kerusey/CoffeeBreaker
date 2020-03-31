from Validation import Visualizer
from Barista import Barista
import Request
import time
import os

def scanActivity():
	currentToken = Visualizer.visualNewSession()
	Request.postTokenStatus("READYTOSCAN")

	timer = 2
	while(Request.getTokenStatus() == "READYTOSCAN"):
		time.sleep(timer)
		timer += 2
		if (timer >= 90):
			timer = 2

	validationStatus = Visualizer.validateToken(currentToken)
	Request.postTokenStatus(validationStatus)
	if(validationStatus == "FAILED"):
		Visualizer.stopSession()
		return token, True
	return token, False

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
	Request.postOrderBd(jsonOrder, generalToken)
	print(jsonOrder)
	# Barista.make(jsonOrder)
	Visualizer.stopSession()
	errorStatus = False
	if(errorStatus):
		Visualizer.visulizeErrorMessage()

while(True):
	main()

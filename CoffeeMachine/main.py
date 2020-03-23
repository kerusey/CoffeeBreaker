from Validation import Visualizer
from Barista import Order
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
		return True
	
	return False
	
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
		flag = scanActivity()
	jsonOrder = finalOrder()
	print(jsonOrder)
	# Barista.make(jsonOrder) 
	
	
main()

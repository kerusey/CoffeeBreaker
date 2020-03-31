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
		return token, True
<<<<<<< HEAD

	return token, False

=======
	
	return token, False
	
>>>>>>> fba78621e64bd20e79c8af082fc79f3c4b1c8809
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

while(True):
	main()

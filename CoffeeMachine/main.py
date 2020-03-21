from Validation import Visualizer
from Barista import Order
import Request
import time

def scanActivity():
	currentToken = Visualizer.visualNewSession()
	currentToken = 0
	Request.postTokenStatus("READYTOSCAN")

	timer = 1
	while(Request.getTokenStatus() == "READYTOSCAN"):
		time.sleep(timer)
		timer += 1
		if (timer >= 90):
			timer = 1      # OK

	validationStatus = Visualizer.validateToken(currentToken)
	Request.postTokenStatus(validationStatus)
	if(validationStatus == "FAILED"):
		Visualizer.stopSession()
		os.remove(ssesion.png) # FAILCHECK
		time.sleep(10)
		return True

	return False

def finalOrder():
	Request.postOrderStatus("WAITING")

	timer = 1
	while(Request.getOrderStatus() == "WAITING"):
		time.sleep(timer)
		timer += 1
		if (timer >= 90):
			timer = 1      # OK

	return Request.getOrder()


def main():
	flag = True
	while flag:
		flag = scanActivity()
	jsonOrder = finalOrder()

	# Barista.make(jsonOrder)



main()

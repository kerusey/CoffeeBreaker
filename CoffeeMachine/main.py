
from Validation import Visualizer
from Barista import Order
import Request

def validateToken(currentToken):
	validationStatus = Visualizer.validateToken(currentToken)
	if(validationStatus):
		Request.postTokenStatus("OK")
		return currentToken, True
	else:
		Request.postTokenStatus("FAILED")
		Visualizer.stopSession()
		currentToken = Visualizer.visualNewSession()
		return currentToken, False
		

def main():
	currentToken = Visualizer.visualNewSession()
	tokenStatus = False
	while(not tokenStatus):
		currentToken, tokenStatus = validateToken(currentToken)
		
		
	

main()


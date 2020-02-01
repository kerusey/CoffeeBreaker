
from Validation import Visualizer
from Barista import Order
import Request

def main():
	currentToken = Visualizer.visualNewSession()
	validationStatus = Visualizer.validateToken(currentToken)
	if(not validationStatus):
            Visualizer.stopSession()

main()

from .Host import ESPInterface

class Barista:
	coffeeType = 'espresso'
	strength = 2
	volume = 4
	milk = 1
	sugar = 0
	apparatusID = 0

	def __init__(self, order:dict, apparatusID:int):
		"""Converts Json order to Barista class."""
		poles = ("coffeeType", "strength", "volume", "milk", "sugar")
		for item in poles:
			self.item = order[item]
		self.apparatusID = apparatusID

	def convert(slef): # TODO develop the way of making coffee
		"""Converts Barista-class to list(queue) of matrix"""
		pass

	def run(self):
		"""
		Establishes connection CoffeeCluster <-=-> CM-ESP32-Apparatus;
		"""
		status = ESPInterface.postToApparatus(self.convert())
		return status

	def __del__(self):
		"""Restores default coffee machine parameters."""
		pass

	def __str__(self):
		"""Returns info 'bout class"""
		order = {
			"coffeeType": self.coffeeType,
			"strength": self.strength,
			"milk": self.milk,
			"volume": self.volume,
			"sugar": self.sugar
		}
		return str(order) + " on apparatus # " + str(self.apparatusID)

def makeOrder(machine:int, order:dict):
	status = Barista(order, apparatusID=machine).run()
	return status

finalOrder = {
	"MachineID": 1,
	"coffeeType": 'latte',
	"strength": 4,
	"volume": 2,
	"milk": 0,
	"sugar": 0
}

makeOrder(machine=1, order=finalOrder) # DEBUGMODE 

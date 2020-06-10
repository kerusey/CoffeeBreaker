# from Host import host

class Barista:
	coffeeType = 'espresso'
	strength = 2
	volume = 4
	milk = 1
	sugar = 0

	def __init__(self, coffeeType, strength, volume, milk, sugar):
		"""Convert Json order to Barista class"""
		self.coffeeType = coffeeType
		self.strength = strength
		self.volume = volume
		self.milk = milk
		self.sugar = sugar

	def run(self):
		pass

	def __del__(self):
		"""Restore default coffee machine parameters"""
		pass

def make(jsonOrder):
	pass

finalOrder ={"MachineID": 1,
            "coffeeType": 'latte',
            "strength": 4,
            "volume": 2,
            "milk": 0,
            "sugar": 0
            }

# make(finalOrder)

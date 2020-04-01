#	barista lib
import RPi.GPIO as GPIO
try:
	from Oscilloscope import Imitator
except ImportError:
	from .Oscilloscope import Imitator
import time

class Barista:
	coffeeType = 'espresso'
	strength = 2
	volume = 4
	milk = 1
	sugar = 0

	def __init__(self, coffeeType, strength, volume, milk, sugar):
		self.coffeeType = coffeeType
		self.strength = strength
		self.volume = volume
		self.milk = milk
		self.sugar = sugar

	def run(self):
		Imitator.keyEvent(coffeeType + ".matrix")

		Imitator.keyEvent("strength.matrix")
		for i in strength:
			Imitator.keyEvent("up.matrix")

		Imitator.keyEvent("volume.matrix")
		for i in volume:
			Imitator.keyEvent("up.matrix")


	def __del__(self):
		Imitator.keyEvent("strength.matrix")

		for i in strength:
			Imitator.keyEvent("down.matrix")

		Imitator.keyEvent("volume.matrix")
		for i in volume:
			Imitator.keyEvent("down.matrix")


def make(jsonOrder):
	coffeeCup = Barista(jsonOrder['coffeeType'],
						jsonOrder['strength'],
						jsonOrder['volume'],
						jsonOrder['milk'],
						jsonOrder['sugar'])
	coffeeCup.run()

finalOrder ={"MachineID": 1,
            "coffeeType": 'latte',
            "strength": 4,
            "volume": 2,
            "milk": 0,
            "sugar": 0
            }
            
# make(finalOrder)

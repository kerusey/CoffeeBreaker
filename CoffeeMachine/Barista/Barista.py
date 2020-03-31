#	barista lib
# import RPi.GPIO as GPIO
from Oscilloscope import Imitator
import time

# also custom stuff like tseh 95 does

class Barista:
	coffeeType
	strenght
	volume
	milk
	sugar

	def __init__(self, coffeeType, strenght, volume, milk, sugar):
		self.coffeeType = coffeeType
		self.strenght = strenght
		self.volume = volume
		self.milk = milk
		self.sugar = sugar


	def run(self):
		Imitator.keyEvent("coffeeType.matrix")

		Imitator.keyEvent("strenght.matrix")
		for i in strenght:
			Imitator.keyEvent("up.matrix")

		Imitator.keyEvent("volume.matrix")
		for i in volume:
			Imitator.keyEvent("up.matrix")


	def __del__(self):
		Imitator.keyEvent("strenght.matrix")

		for i in strenght:
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

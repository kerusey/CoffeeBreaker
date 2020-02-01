#	barista lib
import RPi.GPIO as GPIO
import Order
import time

UP_RELAY = 1
DOWN_RELAY = 2
espresso_RELAY = 3
capuchino_RELAY = 4
crema_RELAY = 5
latte_RELAY = 6
latteM_RELAY = 7
americano_RELAY = 8
strenght_RELAY = 9
volume_RELAY = 10

def relaySetup ():

	GPIO.setmode(GPIO.BOARD)

	GPIO.setup(UP_RELAY, GPIO.OUT)
	GPIO.setup(DOWN_RELAY, GPIO.OUT)
	GPIO.setup(espresso_RELAY, GPIO.OUT)
	GPIO.setup(capuchino_RELAY, GPIO.OUT)
	GPIO.setup(crema_RELAY, GPIO.OUT)
	GPIO.setup(latte_RELAY, GPIO.OUT)
	GPIO.setup(latteM_RELAY, GPIO.OUT)
	GPIO.setup(americano_RELAY, GPIO.OUT)
	GPIO.setup(strenght_RELAY, GPIO.OUT)
	GPIO.setup(volume_RELAY, GPIO.OUT)
	GPIO.setup(START_RELAY, GPIO.OUT)
	GPIO.setup(STOP_RELAY, GPIO.OUT)

	GPIO.output(UP_RELAY, GPIO.LOW)
	GPIO.output(DOWN_RELAY, GPIO.LOW)
	GPIO.output(espresso_RELAY, GPIO.LOW)
	GPIO.output(capuchino_RELAY, GPIO.LOW)
	GPIO.output(crema_RELAY, GPIO.LOW)
	GPIO.output(latte_RELAY, GPIO.LOW)
	GPIO.output(latteM_RELAY, GPIO.LOW)
	GPIO.output(americano_RELAY, GPIO.LOW) # LOW - closed
	GPIO.output(strenght_RELAY, GPIO.LOW)
	GPIO.output(volume_RELAY, GPIO.LOW)
	GPIO.output(START_RELAY, GPIO.LOW)
	GPIO.output(STOP_RELAY, GPIO.LOW)

relaySetup();

espresso = 3
capuchino = 4
crema = 5
latte = 6
latteM = 7
americano = 8

# also custom stuff like tseh 95 does

class Barista:

	__DrinkType = espresso
	__strenght = 0
	__volume = 0
	__milk = False
	__chokolate = False
	__canella = False
	__shugar = 0 # 1-3 cubes of shugar

	def setDrink ():
		GPIO.output(DrinkType, GPIO.HIGH)
		time.sleep(750)
		GPIO.output(strenght_RELAY, GPIO.HIGH) # setting drink strenght
		time.sleep(750)
		for i in range (strenght):
			GPIO.output(UP_RELAY, GPIO.HIGH)
			time.sleep(750)

		GPIO.output(volume_RELAY, GPIO.HIGH) # setting drink volume
		for j in range (volume):
			GPIO.output(UP_RELAY, GPIO.HIGH)
			time.sleep(750)

	def __init__ (self):
		setDrink();

	# def addMilk ():

	# def addChokolate ():

	# def addCanella (): # also add time.sleeps !!!

	def addShugar ():
		for i in range (shugar):
			GPIO.output(shugar_RELAY, GPIO.HIGH) # adding shugar
			time.sleep(750)

	def restoreSettings ():
		GPIO.output(strenght_RELAY, GPIO.HIGH) # restoring drink strenght
		time.sleep(750)
		for i in range (strenght):
			GPIO.output(DOWN_RELAY, GPIO.HIGH)
			time.sleep(750)

		GPIO.output(volume_RELAY, GPIO.HIGH) # restoring drink volume
		for j in range (volume):
			GPIO.output(DOWN_RELAY, GPIO.HIGH)
			time.sleep(750)
		GPIO.output(espresso, GPIO.HIGH)

	def coffeeStart():
		addShugar()
		GPIO.output(START_RELAY, GPIO.HIGH)
		time.sleep(60000) # FIXME
		# addMilk()
		# addChokolate()
		# addCanella()
		restoreSettings();
		# here you need to delete the log.json

# my current cup of coffee
coffeeCoup = Barista(Order.getOrder()) # Works pretty fine

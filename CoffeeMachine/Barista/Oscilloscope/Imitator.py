import RPi.GPIO as GPIO

'''
pin1 -> const
pin2 -> var
pin3 -> var
pin4 -> var
pin5 -> var
pin6 -> const

Some of these should be GND & 5V, so they're constant
And we need to write a voltage writer for the variable pins from the data, that we've got from the PyReaderSample.py
'''

pinlist = {1, 2, 3, 4} # FIXME write a -released- ones


def initializePinout():
	GPIO.setmode(GPIO.BOARD)
	for pin in pinlist:
		GPIO.setup(pin, GPIO.OUT)

def keyEvent(matrix):
	for arr in matrix:
		for i in range(len(arr)):
			pwm = GPIO.PWM(pinlist[i], 100)
			pwm.start(arr[i] / 1023 * 100) 

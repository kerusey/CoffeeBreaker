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

pinlist = {'default', 14, 15, 16, 17, "default", None, None} # FIXME write a -released- ones

def initializePinout():
	GPIO.setmode(GPIO.BOARD)
	for pin in pinlist:
		GPIO.setup(pin, GPIO.OUT)

def convertStrToIntMatrix(keyName):
	with open(keyName, "r") as data:
		matrix = []
		for row in data:
			# data.write(int(row))
			wordlist = list(row.split(", "))
			wordlist[len(wordlist)-1] = wordlist[len(wordlist)-1][:-1]
			intlist = []
			for char in wordlist:
				intlist.append(int(char))
			matrix.append(intlist)
		return matrix # OK

def keyEvent(matrixName):
	matrix = convertStrToIntMatrix(matrixName + ".txt")
	for arr in matrix:
		for member in range(len(arr)):
			if(pinlist[member] != 'default' and pinlist[member] != None):
				pwm = GPIO.PWM(pinlist[i], 100)
				pwm.start(arr[i] / 1023 * 100)

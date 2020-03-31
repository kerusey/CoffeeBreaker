
import time
import os.path

import Adafruit_GPIO.SPI as SPI
import Adafruit_MCP3008

times = 10

# Software SPI configuration:
CLK  = 18
MISO = 23
MOSI = 24
CS   = 25
mcp = Adafruit_MCP3008.MCP3008(clk=CLK, cs=CS, miso=MISO, mosi=MOSI)

# Hardware SPI configuration:
# SPI_PORT   = 0
# SPI_DEVICE = 0
# mcp = Adafruit_MCP3008.MCP3008(spi=SPI.SpiDev(SPI_PORT, SPI_DEVICE))

def removeOld():
	if (os.path.isfile("data.txt")):
		os.remove("data.txt")

def loopAndWrite(times=10, defaultString="0, 0, 0, 0, 0, 0, 0, 0"):
	timer = 0
	removeOld()
	with open("data.txt", "w") as f:
		while True:
			values = [0]*8
			for i in range(8):
				values[i] = mcp.read_adc(i)
			if (defaultString != "{0:}, {1:}, {2:}, {3:}, {4:}, {5:}, {6:}, {7:} \n".format(*values)):
				f.write("{0:}, {1:}, {2:}, {3:}, {4:}, {5:}, {6:}, {7:} \n".format(*values))
				timer += 1
			if(timer >= times):
				f.close()
				return 0 # FIXME Set only 4 pin chanel

def getDefaultString():
	with open("data.txt", "w") as f:
		values = [0]*8
		for i in range(8):
			values[i] = mcp.read_adc(i)
		f.write("{0:}, {1:}, {2:}, {3:}, {4:}, {5:}, {6:}, {7:} \n".format(*values))
		f.close() # FIXME Set only 4 pin chanel

def convertStrToIntList(fileName):
	with open(fileName, "r") as data:
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

# 0, 1, 2, 3, 4, 5, 6, 7

'''
defaultStr = getDefaultString()
loopAndWrite(defaultString=defaultStr)

matrix = convertStrToIntList(fileName="data.txt")
print(matrix[0])
'''

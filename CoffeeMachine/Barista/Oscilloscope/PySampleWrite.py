
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

def loopAndWrite(keyName:str, defaultString="0, 0, 0, 0, 0, 0, 0, 0"):
	timer = 0
	removeOld() # rewrite
	with open(keyName, "w") as key:
		while True:
			values = [0]*8
			for i in range(8):
				values[i] = mcp.read_adc(i)
				currentRow = "{0:}, {1:}, {2:}, {3:}, {4:}, {5:}, {6:}, {7:} \n".format(*values)
			if (defaultString != currentRow):
				key.write(currentRow)
				timer += 1
			if(timer >= 1 and defaultString == currentRow):
				key.close()
				return 0 # FIXME Set only 4 pin chanel

def getDefaultString():
	values = [0]*8
	for i in range(8):
		values[i] = mcp.read_adc(i)
	return convertListToString(values)

def dutySycle(keyName:str):
	defaultStr = getDefaultString()
	loopAndWrite(keyName, defaultString) # Writes a <keyname> file wich contains a pure 2-d matrix of a key-pressed event

# 0, 1, 2, 3, 4, 5, 6, 7

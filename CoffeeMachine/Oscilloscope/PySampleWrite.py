
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

def loopAndWrite(times):
	if (os.path.isfile("data.txt")):
		os.remove("data.txt")
	timer = 0
	with open("data.txt", "w") as f:
		while True:
			values = [0]*8
			for i in range(8):
				values[i] = mcp.read_adc(i)
		
			f.write("{0:}, {1:}, {2:}, {3:}, {4:}, {5:}, {6:}, {7:} \n".format(*values))
			timer += 1
			if(timer >= times):
				f.close()
				return 0
		
loopAndWrite(times)

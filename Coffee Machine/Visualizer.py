# usage python3 Visualizer.py > name.png

import qrcode
import string 
import random

def generateString (len = 25):
	return ''.join(random.SystemRandom().choice(string.ascii_letters + string.digits ) for _ in range(len))

Suffix = "#¯\\_(ツ)_/¯#" 
AppLink = "https://coffeebreaker.com"; # SETME FIXME !
UnicID = "1" # SETME FIXME
UnicKey = generateString() # OK 
QRLink = AppLink + Suffix + UnicID + Suffix + UnicKey + Suffix # SAVEME

img = qrcode.make(QRLink)
# img.show()

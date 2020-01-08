import qrcode
import string 
import random

def generateString (len = 25):
	return ''.join(random.SystemRandom().choice(string.ascii_letters + string.digits ) for _ in range(len))

Suffix_const = "#¯\\_(ツ)_/¯#" # OK
AppLink_const = "https://coffeebreaker.com"; # SETME FIXME !
UnicID_const = "1" # SETME FIXME

def generateQRLink ():
    UnicKey = generateString()
    return AppLink_const +  Suffix_const + UnicID_const +  Suffix_const + UnicKey +  Suffix_const, UnicKey # QRLink func

def visualNewSession(QRLink):   # !!! generates NEW token and starts NEW session !!!
    QRLink, token = generateQRLink()
    current_image = qrcode.make(QRLink)
    current_image.show()
    return token
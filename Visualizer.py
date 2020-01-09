import qrcode
import string 
import random
import time
from pynput.keyboard import Key, Controller

def generateString (len = 25):
	return ''.join(random.SystemRandom().choice(string.ascii_letters + string.digits ) for _ in range(len))

Suffix_const = "#¯\\_(ツ)_/¯#" # OK
AppLink_const = "https://coffeebreaker.com" # SETME FIXME !

with open("/home/kerusey/Documents/MachineSettings.json") as json_file:
        MachineSettings = json.load(json_file)

MachineID = MachineSettings['MachineID']

def generateQRLink ():
    UnicKey = generateString()
    return AppLink_const +  Suffix_const + MachineID +  Suffix_const + UnicKey +  Suffix_const, UnicKey # QRLink func

def fullscreen():
    time.sleep(0.5)
    keyboard = Controller()
    keyboard.press(Key.ctrl)
    keyboard.press(Key.alt)
    keyboard.press("x")
    keyboard.release("x")
    keyboard.release(Key.ctrl)
    keyboard.release(Key.alt) # set ctrl + alt + x as a hotkey n settings !!!
    

def visualNewSession():   # !!! generates NEW token and starts NEW session !!!
    QRLink, token = generateQRLink()
    current_image = qrcode.make(QRLink)
    current_image.show()
    fullscreen()
    return token

def closeWindow(): # OK
    time.sleep(0.5)
    keyboard = Controller()
    keyboard.press(Key.alt)
    keyboard.press(Key.f4)
    keyboard.release(Key.alt)
    keyboard.release(Key.f4)

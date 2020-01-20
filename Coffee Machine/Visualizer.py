import qrcode
import string 
import random
import time
import json
from pynput.keyboard import Key, Controller
from guizero import App, Picture

def generateString (len = 25):
	return ''.join(random.SystemRandom().choice(string.ascii_letters + string.digits ) for _ in range(len))

Suffix_const = "#¯\\_(ツ)_/¯#" # OK
AppLink_const = "https://coffeebreaker.com" # SETME FIXME !

with open("/home/pi/Documents/MachineSettings.json") as json_file:
        MachineSettings = json.load(json_file)

MachineID = MachineSettings['MachineID']

def generateQRLink ():
    Token = generateString()
    return AppLink_const +  Suffix_const + str(MachineID) +  Suffix_const + Token +  Suffix_const, Token # QRLink func

def fullscreen():
    time.sleep(0.1)
    keyboard = Controller()
    keyboard.press(Key.alt)
    keyboard.press(Key.space)
    time.sleep(0.5)
    keyboard.press("x")
    keyboard.release("x")
    keyboard.release(Key.space)
    keyboard.release(Key.alt) # set ctrl + alt + x as a hotkey n settings !!!
    

def visualNewSession():   # !!! generates NEW token and starts NEW session !!!
    QRLink, token = generateQRLink()
    current_image = qrcode.make(QRLink)
    current_image.save("session.png", "PNG")
    app = App(title="CoffeeBreaker", width=800, height=480)
    pic = Picture(app, image="session.png")
    app.display()
    return token

def closeWindow(): # OK
    time.sleep(0.5)
    keyboard = Controller()
    keyboard.press(Key.alt)
    keyboard.press(Key.f4)
    keyboard.release(Key.alt)
    keyboard.release(Key.f4)

# fullscreen()
# visualNewSession()

import qrcode
import string 
import time
import json
import os
import sys
from pynput.keyboard import Key, Controller
from guizero import App, Picture
import datetime
from PIL import Image
Request = sys.path.insert(1, '/home/kerusey/CoffeeMachine/Request.py')
import Request


def generateString ():
	return str(datetime.datetime.now())

Suffix_const = "#¯\\_(ツ)_/¯#" # OK
AppLink_const = "https://coffeebreaker.com" # SETME FIXME !
MachineSettingsPath = "/home/kerusey/Documents/MachineSettings.json"    

with open(MachineSettingsPath) as json_file:
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
    currentQRCode = qrcode.QRCode( 
        version = 1,
        error_correction = qrcode.constants.ERROR_CORRECT_M,
        box_size = 15,
        border = 3,
        )
    currentQRCode.add_data(QRLink)
    currentQRCode.make(fit = True)
    current_image = currentQRCode.make_image(fill_color = "black", back_color = "white")
    current_image.save("session.png", "PNG")
    session = Image.open("session.png").show()
    return token

def stopSession(): # OK
    time.sleep(1)
    keyboard = Controller()
    keyboard.press(Key.alt)
    keyboard.press(Key.f4)
    keyboard.release(Key.alt)
    keyboard.release(Key.f4)
    os.remove("session.png")

def validateToken(token):
    if (Request.getToken() != token): 
        return False
    else:
        return True

# fullscreen()

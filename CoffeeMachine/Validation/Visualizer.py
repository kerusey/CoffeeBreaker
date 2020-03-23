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
Request = sys.path.insert(1, '/home/pi/CoffeeBreaker/CoffeeMachine')
import Request

def generateString ():
    # return str(datetime.datetime.now())
    return "abc"
    
Suffix_const = "#" # OK
AppLink_const = "market://details?id=space.fstudio.lio.coffeebreaker" # SETME FIXME !
MachineSettingsPath = "/home/pi/Documents/MachineSettings.json"

with open(MachineSettingsPath) as json_file:
        MachineSettings = json.load(json_file)

MachineID = MachineSettings['MachineID']

def generateQRLink ():
    Token = generateString()
    return AppLink_const + Suffix_const + str(MachineID) + Suffix_const + Token, Token # QRLink func

def fullscreen():
    time.sleep(1)
    
    keyboard = Controller()
    keyboard.press(Key.alt)
    
    time.sleep(0.4)
    keyboard.press(Key.f11)
    time.sleep(0.4)
    keyboard.release(Key.f11)
    
    keyboard.release(Key.alt) # OK

def visualNewSession():   # !!! generates NEW token and starts NEW session !!!
    QRLink, token = generateQRLink()
    currentQRCode = qrcode.QRCode(
        version = 2,
        error_correction = qrcode.constants.ERROR_CORRECT_H,
        box_size = 9,
        border = 2,
        )
    currentQRCode.add_data(QRLink)
    currentQRCode.make(fit = True)
    current_image = currentQRCode.make_image(fill_color = "black", back_color = "white")
    current_image.save("session.png", "PNG")
    session = Image.open("session.png").show()
    fullscreen()
    return token

def stopSession(): # OK
    time.sleep(1)
    keyboard = Controller()
    keyboard.press(Key.space)
    keyboard.release(Key.space)
    os.remove("session.png")

def validateToken(token):
    if (Request.getToken() != token):
        return "FAILED"
    else:
        return "OK"

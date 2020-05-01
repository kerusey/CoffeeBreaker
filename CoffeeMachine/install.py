from os import listdir, system, getuid
from getpass import getpass
import subprocess
from shutil import move
import time
import threading
import pathlib
from Config import WifiConfig, Services

if (getuid() != 0):
	print("You should run this script with sudo!\n sudo python3 install.py")

toNull = " > /dev/null"

aptHaveToBePreinstalled = ["tightvncserver",
				"sl",
				"sshpass"
				]

pipHaveToBePreinstalled = ["netifaces",
				"pathlib",
				]

aptPackages = [ "build-essential",
		"python-dev",
		"python-smbus",
		"imagemagick",
		"python3-tk",
		"python3-rpi.gpio"
		]

def visualize():
	while(True):
		system("sl")

def clear(): # OK
	system("clear")

def preinstall(): # OK
	system("sudo apt-get -qq update > /dev/null")
	for item in aptHaveToBePreinstalled:
		system("sudo apt-get -qq install " + item + toNull)
	for item in pipHaveToBePreinstalled:
		system("sudo pip3 -q install " + item + toNull)

def shellRun(name:str): # OK
	system("sudo chmod +x ", name)
	system("sudo ./" + name + " > logs.log 2> /dev/null")
	system("sudo rm " + name)
	system("sudo rm logs.log") # optional

def front(threadName):
	clear()
	preinstall()
	Services.initSshServer()
	Services.initVncServer()
	Services.initFtpServer()
	WifiConfig.setWifiConfig()
	time.sleep(3)
	clear()
	backThread = threading.Thread(target=back, args=("backThread_TH",))
	backThread.start()
	clear()
	visualize()

try:
	frontThread = threading.Thread(target=front, args=("frontThread_TH",))
	frontThread.start()
except:
	print("Error: unable to start thread")

def back(threadName, frontThread=frontThread):
	time.sleep(1)
	system("sudo curl -s https://bootstrap.pypa.io/get-pip.py -o get-pip.py " + toNull) # OK
	system("sudo apt-get upgrade --force-yes -qq" + toNull) # OK
	system("sudo cp MachineSettings.json /home/pi/Documents" + toNull)
	system("sudo python3 -s get-pip.py -qq" + toNull)
	system("sudo rm get-pip.py")

	for item in aptPackages:
		system("sudo apt-get -qq install " + item + toNull)

	system("curl -s https://processing.org/download/install-arm.sh -o install-arm.sh" + toNull)
	shellRun("install-arm.sh")
	system("rm nohup.out")

	exit()

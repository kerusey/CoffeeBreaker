from os import listdir, system, getuid
from getpass import getpass
import subprocess
from shutil import move
import time
import threading
import pathlib
from config import Services, WifiConfig

if (getuid() != 0):
	print("You should run this script with sudo!\n sudo python3 install.py")

prefix = ["sudo"]
suffix = [">", "/dev/null"]

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

def shellRun(name:str): # OK
	subprocess.call([*prefix, "chmod", "+x", name])
	subprocess.call([*prefix, "./", name, "-qq", *suffix])
	subprocess.call([*prefix, "rm", name])

def clear(): # OK
	subprocess.call(["clear"])

def preinstall(): # OK
	subprocess.call([*prefix, "apt-get", "-qq", "update"])
	for item in aptHaveToBePreinstalled:
		subprocess.call([*prefix, "apt-get", "-qq", "install", item])
	for item in pipHaveToBePreinstalled:
		subprocess.call([*prefix, "pip3", "-q", "install", item])

def shellRun(name:str): # OK
	subprocess.call([*prefix, "chmod", "+x", name])
	system("sudo ./" + name + " > logs.log 2> /dev/null")
	subprocess.call([*prefix, "rm", name])
	subprocess.call([*prefix, "rm", "logs.log"]) # optional

def front(threadName):
	clear()
	preInstallThread = threading.Thread(target=preinstall)
	preInstallThread.start()
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
	subprocess.call([*prefix, "curl", "-s", "https://bootstrap.pypa.io/get-pip.py", "-o", "get-pip.py", *suffix]) # OK
	subprocess.call([*prefix, "apt-get", "upgrade", "--force-yes", "-qq"]) # OK
	subprocess.call([*prefix, "cp", "MachineSettings.json",  "/home/pi/Documents"])
	subprocess.call([*prefix, "python3", "-s", "get-pip.py", "-qq"])
	subprocess.call([*prefix, "rm", "get-pip.py"])

	for item in aptPackages:
		subprocess.call([*prefix, "apt-get", "-qq", "install", item])

	subprocess.call(["curl", "-s", "https://processing.org/download/install-arm.sh", "-o", "install-arm.sh"])
	shellRun("install-arm.sh")

	subprocess.call("rm", "nohup.out")

	exit()

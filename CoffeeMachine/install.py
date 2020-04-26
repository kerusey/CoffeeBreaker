# Tests required

import socket
from getpass import getpass
import subprocess
from os import system
import time
import threading

def visualize():
	while(True):
		system("sl")

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

def getLan(): # OK
	interfaces = netifaces.interfaces()
	for i in interfaces:
		if i == 'lo':
			continue
		iface = netifaces.ifaddresses(i).get(netifaces.AF_INET)
		if iface != None:
			for j in iface:
				return str(j['addr'])

def shellRun(name:str): # OK
	subprocess.call([*prefix, "chmod", "+x", name])
	subprocess.call([*prefix, "./", name, "-qq", *suffix])
	subprocess.call([*prefix, "rm", name])

def clear(): # OK
	subprocess.call(["clear"])

def initSshServer(): # OK
	password = str(getpass())
	subprocess.call(["sudo", "systemctl", "enable", "ssh"])
	subprocess.call(["sshpass", "-p", password, "ssh", "pi@"+getLan()])
	subprocess.call(["sudo", "systemctl", "start", "ssh"])

def initVncServer(): # OK
	with open("/root/.vnc/config.d/vncserver-x11", "w") as serverWriteable, open("config/vncserver-x11", "r") as serverReadable:
		serverWriteable.write(serverReadable.read())

	print("Enter VNC password:")
	subprocess.call([*prefix, "vncserver"])

	pathlib.Path("/home/pi/.config/autostart").mkdir(parents=True, exist_ok=True)
	with open("/home/pi/.config/autostart/tightvnc.desktop", "w") as writeable, open("config/tightvnc.desktop", "r") as readabe: # adding to the autostart
		writeable.write(readabe.read())


def initWifiAutoconnection(): # TestVersion
	print("Enter SSID: ", end='')
	ssid = str(input())
	print("Enter Key or Password: ", end='')
	password = str(getpass())

	with open("/etc/network/interfaces", "w") as writeable, open("config/interfaces", "r") as readable:
		for row in readable:
			writeable.write(readable.read())
		writeable.write("wpa-ssid " + ssid)
		writeable.write("wpa-psk " + password)

	with open("/etc/wpa_supplicant/wpa_supplicant.conf", "w+") as writeable, open("config/wpa_supplicant.conf") as readabe:
		for number, row in enumerate(readabe):
			writeable.write(readabe.read())
		writeable.write('ssid="SSID"'.format(SSID=ssid))
		writeable.write('psk="KEY"'.format(psk=password))
		writeable.write("}")

def preinstall(): # TestVersion
	subprocess.call([*prefix, "apt-get", "-qq", "update"])
	for item in aptHaveToBePreinstalled:
		subprocess.call([*prefix, "apt-get", "-qq", "install", item])
	for item in pipHaveToBePreinstalled:
		subprocess.call([*prefix, "pip3", "-q", "install", item])
		import item


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

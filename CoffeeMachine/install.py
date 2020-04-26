# Tests required

from getpass import getpass
import subprocess
from os import *
import time
import threading

def visualize():
	while(True):
		system("sl")


prefix = ["sudo"]
suffix = [">", "/dev/null"]

def shellRun(name:str):
	subprocess.call([*prefix, "chmod", "+x", name])
	subprocess.call([*prefix, "./", name, "-qq", *suffix])
	subprocess.call([*prefix, "rm", name])


'''
def getItemInPrefix(prefix:list):
	arr = ()
	for item in prefix:
		arr += (item,)
	return arr

print(type(getItemInPrefix(prefix)))
prefix = getItemInPrefix(prefix)
'''

aptPackages = [ "build-essential",
				"python-dev",
				"python-smbus",
				"imagemagick",
				"python3-tk",
				"python3-rpi.gpio",
				"tightvncserver",
				"pure-ftpd"
				]

services = ["ssh", "vncserver", "ftp"]

def clear():
	subprocess.call(["clear"])

def initFtpServer():
	print("Enter FTP password:")
	subprocess.call([*prefix, "groupadd", "ftpgroup"])
	subprocess.call([*prefix, "useradd", "ftpuser", "-g", "ftpgroup", "-s", "/sbin/nologin", "-d", "/dev/null"])
	subprocess.call([*prefix, "pure-pw", "useradd", "upload", "-u", "ftpuser", "-g" "ftpgroup", "-d", "/home/pi", "-m"])
	subprocess.call([*prefix, "pure-pw", "mkdb"])
	subprocess.call([*prefix, "service", "pure-ftpd", "restart"])

def preinstall():
	try:
		subprocess.call([*prefix, "apt-get", "-qq", "install", "sl"])
	except:
		time.sleep(2)
		print("awaiting for dpkg")
		preinstall()
	initFtpServer()

def initWifiAutoconnection(login:str, password:str):
	try:
		with open("/etc/network/interfaces", "w+") as writeable, open("config/interfases") as readable:
			for row in readable:
				writeable.write(readable.read())
	except:
		print("failed to initialize interfaces")
		return None
	try:
		with open("/etc/wpa_supplicant/wpa_supplicant.conf", "w+") as writeable, open("config/wpa_supplicant.conf") as readabe:
			for number, row in enumerate(readabe):
				writeable.write(readabe.read())
			writeable.write('ssid="SSID"'.format(SSID=login))
			writeable.write('psk="KEY"'.format(psk=password))
			writeable.write("}")
	except:
		print("failed to initialize wpa_supplicant.conf")
		return None
	return 0

def shellRun(name:str):
	subprocess.call([*prefix, "chmod", "+x", name])
	system("sudo ./" + name + " > logs.log 2> /dev/null")
	subprocess.call([*prefix, "rm", name])
	subprocess.call([*prefix, "rm", "logs.log"]) # optional

def front(threadName):
	clear()
	preInstallThread = threading.Thread(target=preinstall)
	preInstallThread.start()
	time.sleep(3)
	print("Enter SSID: ", end='')
	ssid = str(input())
	print("Enter Key or Password: ", end='')
	password = str(getpass())
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
	subprocess.call([*prefix, "apt-get", "update", "-qq"]) # OK
	subprocess.call([*prefix, "apt-get", "upgrade", "--force-yes", "-qq"]) # OK
	subprocess.call([*prefix, "cp", "MachineSettings.json",  "/home/pi/Documents"])
	subprocess.call([*prefix, "python3", "-s", "get-pip.py", "-qq"])
	subprocess.call([*prefix, "rm", "get-pip.py"])

	for item in aptPackages:
		subprocess.call([*prefix, "apt-get", "-qq", "install", item])

	subprocess.call(["curl", "-s", "https://processing.org/download/install-arm.sh", "-o", "install-arm.sh"])
	shellRun("install-arm.sh")

	for service in services:
		subprocess.call([*prefix, "systemctl", "enable", service])
		subprocess.call([*prefix, "systemctl", "start", service])

	initWifiAutoconnection(ssid, password)

	subprocess.call("rm", "nohup.out")

	exit()

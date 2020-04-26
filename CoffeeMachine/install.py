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

haveToBePreinstalled = ["tightvncserver",
						"pure-ftpd",
						"sl",
						"sshpass"
						]

aptPackages = [ "build-essential",
				"python-dev",
				"python-smbus",
				"imagemagick",
				"python3-tk",
				"python3-rpi.gpio"
				]

# services = ["ssh", "vncserver", "ftp"]

def clear():
	subprocess.call(["clear"])

def initSshServer():
	password = str(getpass())
	subprocess.call(["sshpass", "-p", password, "ssh", "pi@"+str(socket.gethostbyname(socket.gethostname()))])
	subprocess.call(["sudo", "systemctl", "enable", "ssh"])
	subprocess.call(["sudo", "systemctl", "start", "ssh"])

def initVncServer(): # TestME
	with open("/root/.vnc/config.d/vncserver-x11", "w") as writable, open("config/vncserver-x11", "r") as readable:
		writeable.write(readabe.read())

	print("Enter VNC password:")
	subprocess.call(["sudo", "vncpasswd -service"])

	pathlib.Path("/home/pi/.config/autostart").mkdir(parents=True, exist_ok=True)
	with open("/home/pi/.config/autostart/tightvnc.desktop", "w") as writeable, open("config/tightvnc.desktop", "r") as readabe: # adding to the autostart
		writeable.write(readabe.read())


def initFtpServer():
	subprocess.call([*prefix, "groupadd", "ftpgroup"])
	subprocess.call([*prefix, "useradd", "ftpuser", "-g", "ftpgroup", "-s", "/sbin/nologin", "-d", "/dev/null"])
	subprocess.call([*prefix, "pure-pw", "useradd", "upload", "-u", "ftpuser", "-g" "ftpgroup", "-d", "/home/pi", "-m"])
	subprocess.call([*prefix, "pure-pw", "mkdb"])
	subprocess.call([*prefix, "service", "pure-ftpd", "restart"])

def initWifiAutoconnection(): # TestME
	with open("/etc/network/interfaces", "w") as writeable, open("config/interfaces", "r") as readable:
		for row in readable:
			writeable.write(readable.read())
	'''
		print("failed to initialize interfaces")
		return None
	'''
	
	print("Enter SSID: ", end='')
	ssid = str(input())
	print("Enter Key or Password: ", end='')
	password = str(getpass())
	with open("/etc/wpa_supplicant/wpa_supplicant.conf", "w+") as writeable, open("config/wpa_supplicant.conf") as readabe:
		for number, row in enumerate(readabe):
			writeable.write(readabe.read())
		writeable.write('ssid="SSID"'.format(SSID=ssid))
		writeable.write('psk="KEY"'.format(psk=password))
		writeable.write("}")

def preinstall():# TestME
	try:
		for item in haveToBePreinstalled:
			subprocess.call([*prefix, "apt-get", "-qq", "install", item])
	except:
		time.sleep(2)
		print("awaiting for dpkg")
		preinstall()

	print("Enter SSH password:")
	initSshServer()
	
	print("Enter FTP password:")
	initFtpServer()
	
	print("Enter VNC password:")
	initVncServer()


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

	subprocess.call("rm", "nohup.out")

	exit()

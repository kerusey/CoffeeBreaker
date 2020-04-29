from getpass import getpass
import subprocess
from os import system, getuid
import time
import threading
import pathlib

if (getuid() != 0):
	print("You should run this script with sudo!\n sudo python3 test.py")

prefix = ["sudo"]
suffix = [">", "/dev/null"]

aptHaveToBePreinstalled = ["tightvncserver",
			   "sl",
			   "sshpass",
			   "pure-ftpd"
			  ]

pipHaveToBePreinstalled = ["netifaces",
			   "pathlib",
			  ]

def preinstall(): # OK
	subprocess.call([*prefix, "apt-get", "-qq", "update"])
	for item in aptHaveToBePreinstalled:
		subprocess.call([*prefix, "apt-get", "-qq", "install", item])
	for item in pipHaveToBePreinstalled:
		subprocess.call([*prefix, "pip3", "-q", "install", item])

preinstall()

def initFtpServer(): # OK
	subprocess.call([*prefix, "groupadd", "ftpgroup"])
	subprocess.call([*prefix, "useradd", "ftpuser", "-g", "ftpgroup", "-s", "/sbin/nologin", "-d", "/dev/null"])
	subprocess.call([*prefix, "pure-pw", "useradd", "upload", "-u", "ftpuser", "-g" "ftpgroup", "-d", "/home/pi", "-m"])
	subprocess.call([*prefix, "pure-pw", "mkdb"])
	subprocess.call([*prefix, "service", "pure-ftpd", "restart"])

def initWifiAutoconnection(): # TestVersion
	print("Enter SSID: ", end='')
	ssid = str(input())
	print("Enter Key or Password: ", end='')
	password = str(getpass())

	with open("/etc/network/interfaces", "w") as writeable, open("config/interfaces", "r") as readable:
		for row in readable:
			writeable.write(readable.read())

	with open("/etc/wpa_supplicant/wpa_supplicant.conf", "w+") as writeable, open("config/wpa_supplicant.conf") as readabe:
		for number, row in enumerate(readabe):
			writeable.write(readabe.read())
		writeable.write('  ssid="' + ssid + '"\n')
		writeable.write('  psk="' + password + '"\n')
		writeable.write("}")
	print()
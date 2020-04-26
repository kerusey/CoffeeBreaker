
from getpass import getpass
import subprocess
from os import system
import time
import threading
import pathlib

prefix = ["sudo"]
suffix = [">", "/dev/null"]


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



initWifiAutoconnection()

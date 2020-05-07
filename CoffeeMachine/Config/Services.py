from getpass import getpass
import subprocess
from os import system
import pathlib
import string

vncAutostartScript = """
[Desktop Entry]
Type=Application
Name=TightVNC
Exec=vncserver :1
StartupNotify=false
"""

def vncautostart():
	system("sudo mkdir /home/" + GLOBALUSERNAME + "")

def getLan(): # OK
	import netifaces
	interfaces = netifaces.interfaces()
	for i in interfaces:
		if i == 'lo':
			continue
		iface = netifaces.ifaddresses(i).get(netifaces.AF_INET)
		if iface != None:
			for j in iface:
				return str(j['addr'])
				def initSshServer(): # OK

	print("Creating a new user...")
	print("Enter new login: ", end="")
	userName = str(input())
	password = str(getpass())
	system("sudo /usr/sbin/useradd --groups sudo -m " + userName + " -p " + password)
	print("New user has been successfully added.")
	''' # Experemental convertation !
	print("Converting data between accounts...")
	sourcePath = "/home/pi/"
	destinationPath = "/home/" + userName
	source = listdir(sourcePath)
	for item in source:
		try:
			move(sourcePath + item, destinationPath)
		except:
			print("exception")

	subprocess.call(["sudo", "deluser", "--remove", "pi"])
	'''
	print("Establishing SSH server...")
	system("sudo passwd " + userName)
	system("sshpass -p " + password + " ssh " + userName + "@" + getLan())
	system("sudo systemctl enable ssh")
	system("sudo systemctl start ssh")
	return userName

def initVncServer(userName): # OK
	print("Enabling VNC protocol...")
	system("sudo vncserver")

	pathlib.Path("/home/" + userName + "/.config/autostart").mkdir(parents=True, exist_ok=True)
	with open("/home/" + userName + "/.config/autostart/tightvnc.desktop", "w") as writeable:# adding to the autostart
		writeable.write(vncAutostartScript)

def initFtpServer(): # OK
	system("sudo groupadd ftpgroup")
	system("sudo useradd ftpuser -g ftpgroup -s /sbin/nologin -d /dev/null")
	system("sudo pure-pw useradd upload -u ftpuser -g ftpgroup -d /home/pi", "-m")
	system("sudo pure-pw mkdb")
	system("sudo service pure-ftpd restart")

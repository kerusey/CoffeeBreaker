from getpass import getpass
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
	print("Establishing SSH server...")
	system("sudo passwd " + userName)
	system("sshpass -p " + password + " ssh " + userName + "@" + getLan())
	system("sudo systemctl enable ssh")
	system("sudo systemctl start ssh")
	return userName

def initVncServer(userName): # OK
	print("Enabling VNC protocol...")
	password = str(getpass())
	system("sudo vncserver -p " + password)
	autostartPath = "/home/" + userName + "/.config/autostart/"

	pathlib.Path(autostartPath).mkdir(parents=True, exist_ok=True)
	with open(autostartPath + "tightvnc.desktop", "w") as vncStart: # adding to the autostart
		vncStart.write(vncAutostartScript)
	with open("autostart.sh", 'a') as vncStart:
		vncStart.write("vncserver -p " + password)

	system("sudo cp autostart.sh /etc/init.d/autostart.sh")
	system("sudo update-rc.d autostart.sh defaults")
	system("sudo chmod +x /etc/init.d/autostart.sh")
	system("rm autostart.sh")

def initFtpServer(): # OK
	print("Establish FTP...")
	system("sudo groupadd ftpgroup")
	system("sudo useradd ftpuser -g ftpgroup -s /sbin/nologin -d /dev/null")
	system("sudo pure-pw useradd upload -u ftpuser -g ftpgroup -d /home/pi -m")
	system("sudo pure-pw mkdb")
	system("sudo service pure-ftpd restart")

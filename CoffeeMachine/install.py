from os import listdir, system, getuid, chdir
from getpass import getpass
import subprocess
from shutil import move
import time
import threading
import pathlib
from Config import WifiConfig, Services

if (getuid() != 0):
	print("You should run this script with sudo!\nsudo python3 install.py")
	exit()

toNull = " > /dev/null"

aptHaveToBePreinstalled = ["figlet",
	"pure-ftpd",
	"tightvncserver",
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
		"python3-rpi.gpio",
		]

def visual(): # OK
	system("/usr/local/bin/asciiquarium")

def clear(): # OK
	system("clear")

def installAquarium(): # OK
	system("sudo apt-get -qq install libcurses-perl " + toNull)
	chdir("/tmp/")
	system("sudo wget http://search.cpan.org/CPAN/authors/id/K/KB/KBAUCOM/Term-Animation-2.4.tar.gz 2> NUL")
	system("sudo tar -zxf Term-Animation-2.4.tar.gz")
	chdir("/tmp/Term-Animation-2.4/")
	system("perl -X Makefile.PL > NULL && make > NULL && make test > NULL")
	system("sudo make install" + toNull)
	chdir("/tmp/")
	system("wget http://www.robobunny.com/projects/asciiquarium/asciiquarium.tar.gz 2> NUL")
	system("tar -zxf asciiquarium.tar.gz")
	chdir("/tmp/asciiquarium_1.1/")
	system("sudo cp asciiquarium /usr/local/bin")
	system("sudo chmod 0755 /usr/local/bin/asciiquarium")
	chdir("/tmp/")
	system("rm Term-Animation-2.4.tar.gz asciiquarium.tar.gz NUL")
	system("rm -rf Term-Animation-2.4/ asciiquarium_1.1/")

def preinstall(): # OK
	for index, item in enumerate(aptHaveToBePreinstalled):
		system("sudo apt-get -qq install " + item + toNull)
		if (index == 0):
			system("figlet CoffeeBreakerTM")
	installAquarium()
	for item in pipHaveToBePreinstalled:
		system("sudo pip3 -q install " + item + toNull)

preinstall() # OK

def shellRun(name:str): # OK
	system("sudo chmod +x " + name)
	system("sudo ./" + name + " > logs.log 2> /dev/null")
	system("sudo rm " + name)
	system("sudo rm logs.log") # optional

def front(threadName):
	clear()
	Services.initSshServer() # OK
	Services.initVncServer() # OK
	Services.initFtpServer() # OK
	WifiConfig.setWifiConfig() # OK
	backThread = threading.Thread(target=back, args=("backThread_TH",))
	backThread.start()
	visual()
	clear()

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
	system("sudo reboot now")

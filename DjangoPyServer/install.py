from os import system, getuid, chdir
import time
import threading

if (getuid() != 0):
	print("You should run this script with sudo!\nsudo python3 install.py")
	exit()

toNull = " > /dev/null"

aptHaveToBePreinstalled = ["figlet",
	"sl"
	]

pipHaveToBePreinstalled = ["netifaces",
	"pathlib",
	]

aptPackages = [ "build-essential",
		"python-dev",
		"python-smbus",
		"imagemagick",
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
	clear()
	for index, item in enumerate(aptHaveToBePreinstalled):
		system("sudo apt-get -qq install " + item + toNull)
		if (index == 0):
			system("figlet CoffeeBreakerTM")
	installAquarium()
	for item in pipHaveToBePreinstalled:
		system("sudo pip3 -q install " + item + toNull)

preinstall()

def shellRun(name:str): # OK
	system("sudo chmod +x " + name)
	system("sudo ./" + name + " > logs.log 2> /dev/null")
	system("sudo rm " + name)
	system("sudo rm logs.log") # optional

def front(threadName):
	clear()
	backThread = threading.Thread(target=back, args=("backThread_TH",))
	backThread.start()
	clear()
	visual()

try:
	frontThread = threading.Thread(target=front, args=("frontThread_TH",))
	frontThread.start()
except:
	print("Error: unable to start thread")

def back(threadName, frontThread=frontThread):
	time.sleep(1)
	system("sudo apt-get -qq update " + toNull)
	system("sudo apt-get -qq upgrade " + toNull)

	for item in aptPackages:
		system("sudo apt-get -qq install " + item)

	system("sudo pip3 install -r requirements.txt")
	
	system("sudo reboot now")

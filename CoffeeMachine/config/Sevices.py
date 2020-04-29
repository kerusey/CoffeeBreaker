from getpass import getpass

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
	subprocess.call(["sudo", "/usr/sbin/useradd", "--groups", "sudo", "-m", userName, "-p", password])
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
	subprocess.call(["sudo", "passwd", userName])
	system("sshpass -p " + password + " ssh " + userName + "@" + getLan())
	subprocess.call(["sudo", "systemctl", "enable", "ssh"])
	subprocess.call(["sudo", "systemctl", "start", "ssh"])

def initVncServer(): # OK
	with open("/root/.vnc/config.d/vncserver-x11", "w") as serverWriteable, open("config/vncserver-x11", "r") as serverReadable:
		serverWriteable.write(serverReadable.read())

	print("Enter VNC password:")
	subprocess.call(["sudo", "vncserver"])

	pathlib.Path("/home/pi/.config/autostart").mkdir(parents=True, exist_ok=True)
	with open("/home/pi/.config/autostart/tightvnc.desktop", "w") as writeable, open("config/tightvnc.desktop", "r") as readabe: # adding to the autostart
		writeable.write(readabe.read())

def initFtpServer(): # OK
	subprocess.call(["sudo", "groupadd", "ftpgroup"])
	subprocess.call(["sudo", "useradd", "ftpuser", "-g", "ftpgroup", "-s", "/sbin/nologin", "-d", "/dev/null"])
	subprocess.call(["sudo", "pure-pw", "useradd", "upload", "-u", "ftpuser", "-g" "ftpgroup", "-d", "/home/pi", "-m"])
	subprocess.call(["sudo", "pure-pw", "mkdb"])
	subprocess.call(["sudo", "service", "pure-ftpd", "restart"])

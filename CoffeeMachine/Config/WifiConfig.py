import os
import string
from getpass import getpass
from platform import system as system_platform

script_dir = os.path.dirname(os.path.abspath(__file__)) + "/"
interfacesTemplate = """
auto lo

auto wlan0

allow-hotplug wlan0
iface wlan0 inet dhcp
wpa-conf /etc/wpa_supplicant/wpa_supplicant.conf
iface default inet dhcp
"""

wpaSupplicantTemplate = """
ctrl_interface=DIR=/var/run/wpa_supplicant GROUP=netdev
update_config=1
network={
	ssid="$ssid"
	psk="$password"
	key_mgmt=WPA-PSK
	proto=RSN
	pairwise=CCMP
	auth_alg=OPEN
}
"""

def setWifiConfig():
	print("Enter network 2,4 Hhz SSID: ", end='')
	ssid = str(input())
	password = str(getpass())
	iface = interfacesTemplate
	supplicant = string.Template(wpaSupplicantTemplate).substitute({"ssid": ssid, "password": password})

	with open(script_dir + "sessionInterfaces", "wt") as interfaces, open(script_dir + "sessionWpa_supplicant.conf", "wt") as wpa_supplicant:
		interfaces.write(iface)
		wpa_supplicant.write(supplicant)

	if (system_platform() == "Linux"):
		os.system("sudo rm /var/run/wpa_supplicant/wlan0 1> NULL 2> NULL")
		os.system("sudo pkill -f wpa_supplicant.conf 1> NULL 2> NULL")
		os.system("sudo cp " + script_dir + "sessionInterfaces /etc/network/interfaces")
		os.system("sudo cp " + script_dir + "sessionWpa_supplicant.conf /etc/wpa_supplicant/wpa_supplicant.conf")
		os.system("sudo rm -f " + script_dir + "sessionInterfaces")
		os.system("sudo rm -f " + script_dir + "sessionWpa_supplicant.conf")

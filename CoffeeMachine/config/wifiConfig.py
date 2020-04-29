import sys
import getopt
import os
import string
import platform

script_dir = os.path.dirname(os.path.abspath(__file__))

interfaces_template = """

auto lo
iface lo inet loopback
allow-hotplug eth0
iface eth0 inet dhcp

allow-hotplug wlan0
auto wlan0
iface wlan0 inet dhcp
\tpre-up wpa_supplicant -Dwext -i wlan0 -c /etc/wpa_supplicant/wpa_supplicant.conf -B
iface default inet dhcp

"""

wpa_supplicant_template2 = """
ctrl_interface=DIR=/var/run/wpa_supplicant GROUP=netdev
update_config=1
ap_scan=2
network={
	ssid="$ssid"
	scan_ssid=1
	proto=RSN
	pairwise=CCMP
	key_mgmt=WPA-PSK
	psk="$password"
}
"""

wpa_supplicant_template = """
ctrl_interface=DIR=/var/run/wpa_supplicant GROUP=netdev
update_config=1
network={
	ssid="$ssid"
	psk="$password"
}
"""

open_wpa_supplicant_template = """
ctrl_interface=DIR=/var/run/wpa_supplicant GROUP=netdev
update_config=1
ap_scan=2
eapol_version=1
network={
	ssid="$ssid"
	scan_ssid=1
	key_mgmt=NONE
}
"""

def create_wifi_configurations(cls, ssid, password=None):
	intefaces = interfaces_template
	if not password:
		supplicant = string.Template(open_wpa_supplicant_template).substitute({"ssid": ssid})
	else:
		supplicant = string.Template(wpa_supplicant_template).substitute({"ssid": ssid, "password": password})

	return intefaces, supplicant

	with open(script_dir + "/interfaces", "wt") as interfaces, open(script_dir + "/wpa_supplicant.conf", "wt") as wpa_supplicant:
		interfaces.write(iface)
		wpa_supplicant.write(supplicant)

	if platform.system() == "Linux":
		os.system("sudo rm /var/run/wpa_supplicant/wlan0")
		os.system("sudo pkill -f wpa_supplicant.conf")
		os.system("sudo cp ./interfaces /etc/network/")
		os.system("sudo cp ./wpa_supplicant.conf /etc/wpa_supplicant/")
		os.system("sudo rm -f ./interfaces")
		os.system("sudo rm -f ./wpa_supplicant.conf")
		os.system("sudo reboot")

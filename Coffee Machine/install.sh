sudo su -
apt-get update
apt-get upgrade
raspi-config
iwlist wlan0 scan
nano /etc/wpa_supplicant/wpa_supplicant.conf # network = { ssid="SSIDNAME"  psk="SSIDPASSWORD" }
apt-get install python3
curl https://bootstrap.pypa.io/get-pip.py -o get-pip.py
python3 get-pip.py
pip install -U pip
pip install qrcode
pip install
apt-get install realvnc-vnc-server
vncserver :1 # 5901 / 5902
apt-get -y install python3-rpi.gpio

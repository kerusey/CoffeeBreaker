sudo su -
apt-get update
apt-get -y upgrade
apt-get -y install git
git clone https://github.com/kerusey/CoffeeBreaker
cp CoffeeBreaker/MachineSettings.json  /home/pi/Documents
raspi-config
iwlist wlan0 scan
nano /etc/wpa_supplicant/wpa_supplicant.conf # network = { ssid="SSIDNAME"  psk="SSIDPASSWORD" }
apt-get -y install python3
curl https://bootstrap.pypa.io/get-pip.py -o get-pip.py
python3 get-pip.py
pip3 install -U pip
pip3 install Pynput
pip3 install --upgrade google-api-python-client google-auth-httplib2 google-auth-oauthlib
pip3 install qrcode
apt-get -y install realvnc-vnc-server
vncserver :1 # 5901 / 5902
apt-get -y install python3-rpi.gpio
apt-get install python3-tk
mkdir /home/pi/Server

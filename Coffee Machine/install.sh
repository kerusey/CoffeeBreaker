sudo su -
apt-get update
apt-get -y upgrade
apt-get -y install git
raspi-config
iwlist wlan0 scan
nano /etc/wpa_supplicant/wpa_supplicant.conf # network = { ssid="SSIDNAME"  psk="SSIDPASSWORD" }
apt-get -y install python3
curl https://bootstrap.pypa.io/get-pip.py -o get-pip.py
python3 get-pip.py
pip install -U pip
pip install qrcode
apt-get -y install realvnc-vnc-server
vncserver :1 # 5901 / 5902
apt-get -y install python3-rpi.gpio
cd ~/Downloads
apt-get install python3-tk
apt-get install vsftpd
nano /etc/vsftpd.conf # annonymous enble (NO) ; local enable ; write enable ; local umask ; chroot local user ; user sub token ; local root  
cd home/<username> # SETME FIXME
mkdir FTP
mkdir FTP/files
chmod a-w FTP
service vsftpd restart

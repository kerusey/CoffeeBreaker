sudo apt-get update
cp MachineSettings.json  /home/pi/Documents
sudo apt-get -y install python3
curl https://bootstrap.pypa.io/get-pip.py -o get-pip.py
python3 get-pip.py
pip3 install -U pip
pip3 install Pynput
pip3 install qrcode
sudo apt-get -y install python3-rpi.gpio
sudo apt-get install python3-tk
mkdir /home/pi/Server
cd /home/pi/Server/
mkdir Tokens
mkdir TokenStatuses
mkdir Orders
mkdir OrderStatuses
cd
sudo apt-get install imagemagick
rm get-pip.py
sudo raspi-config

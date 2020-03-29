sudo apt-get update
sudo apt-get -y upgrade
cp CoffeeBreaker/MachineSettings.json  /home/pi/Documents
sudo apt-get -y install python3
curl https://bootstrap.pypa.io/get-pip.py -o get-pip.py
python3 get-pip.py
rm get-pip.py
sudo apt-get install build-essential python-dev python-smbus python-pip
sudo pip3 install adafruit-mcp3008
sudo python setup.py install
cd ..
sudo pip3 -r install requrements.txt
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
curl https://processing.org/download/install-arm.sh | sudo sh
rm get-pip.py
raspi-config

sudo apt-get update
sudo apt-get -y upgrade
cp CoffeeBreaker/MachineSettings.json  /home/pi/Documents
sudo apt-get -y install python3
curl https://bootstrap.pypa.io/get-pip.py -o get-pip.py
python3 get-pip.py
sudo apt-get install build-essential python-dev python-smbus git
cd
git clone https://github.com/adafruit/Adafruit_Python_MCP3008.git
cd Adafruit_Python_MCP3008
sudo python setup.py install
cd ..
rm -rf Adafruit_Python_MCP3008
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
raspi-config

sudo su -
apt-get update
apt-get install python3
curl https://bootstrap.pypa.io/get-pip.py -o get-pip.py
python3 get-pip.py
pip install -U pip
pip install qrcode

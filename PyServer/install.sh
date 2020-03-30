#!/bin/bash

Operating_System=lsb_release -i | cut -f 2-
if [ $Operating_System == "CentOS" ]
then
    sudo yum install -y https://centos7.iuscommunity.org/ius-release.rpm;
    sudo yum install figlet;
    figlet -f slant Hello;
    sudo yum update;
    sudo yum install -y python36u python36u-libs python36u-devel python36u-pip;
elif [ $Operating_System == "Ubuntu" || $Operating_System == "Debian" ]
then
    sudo apt-get install figlet;
    figlet -f slant Hello;
    sudo apt update;
    sudo apt install python3;
elif [ $Operating_System == "Arch" || $Operating_System == "ManjaroLinux" ]
then
    sudo pacman -S figlet;
    figlet -f slant Hello;
    sudo pacman -S python3;
else
    echo "Unsupported Operating System. Please install python3 by yourself";
sudo python3 -m pip3 install --upgrade pip
sudo pip3 install -r requirements.txt

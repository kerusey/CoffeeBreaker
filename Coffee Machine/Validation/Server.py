# FTP folder home/pi/FTP/validation
import json
import os

folderPath = "/home/pi/FTP/validation/"
fileName = "checkme.json"

def getToken():
	with open(folderPath + fileName) as json_file:
		json_data = json.load(json_file)
	token = str(json_data)
	token = token[11 : len(token) - 2] # fine
	os.remove(folderPath + fileName)
	return token # OK

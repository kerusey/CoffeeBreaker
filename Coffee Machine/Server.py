# FTP folder home/pi/FTP/validation
import json
import os

with open("/home/pi/Documents/MachineSettings.json") as json_file:
		MachineSettings = json.load(json_file)

folderPath = MachineSettings['logsPath']
fileName = "Validation" + str(MachineSettings['MachineID']) + ".json"
print(folderPath + fileName)

def getToken():
	with open(folderPath + fileName) as json_file:
		json_data = json.load(json_file)
	token = str(json_data)
	token = token[11 : len(token) - 2] # fine
	os.remove(folderPath + fileName)
	return token # OK

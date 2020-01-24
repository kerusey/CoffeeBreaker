import json
import os
import urllib

MachineFolderPath = "/home/kerusey/Server/validation/"
MachineSettingsPath = "/home/kerusey/Documents/MachineSettings.json"    

with open(MachineSettingsPath) as json_file:
		MachineSettings = json.load(json_file)

fileName = "Validation" + str(MachineSettings['MachineID']) + ".json"

def getRequest(fileName):
	urllib.urlretrieve(MachineSettings['Ip'] + MachineSettings['ServerValidationPath'], MachineFolderPath + fileName) # OK

def postRequest(fileName):
	with open(fileName, 'rb') as req:
		req = requests.post(MachineSettings['Ip'] + MachineSettings['ServerValidationPath'], files = {fileName: f})


def getToken():
	with open(folderPath + fileName) as json_file:
		json_data = json.load(json_file)
	token = str(json_data)
	token = token[11 : len(token) - 2] # fine
	os.remove(folderPath + fileName)
	return token # OK

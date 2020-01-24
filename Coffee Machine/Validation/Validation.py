
import Visualizer
import Server
import json


with open(MachineSettingsPath) as json_file:
		MachineSettings = json.load(json_file)

def validateToken(token):
	if (Server.getToken() != token): 
		Visualizer.stopSession()
		Server.Post(MachineSettings['MachineID'] + " terminate")
		return Visualizer.visualNewSession()
	else:
		Server.Post(MachineSettings['MachineID'] + " fine")
		return token

token = serverCheck()

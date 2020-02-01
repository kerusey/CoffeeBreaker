import json
import requests

MachineSettingsPath = "/home/kerusey/CoffeeMachine/MachineSettings.json"

with open(MachineSettingsPath) as json_file:
        MachineSettings = json.load(json_file)

url = "http://" + MachineSettings['Ip'] + ":" + str(MachineSettings['Port']) + "/" # <- insert here method path

def getHelloWorld():
    resp = requests.get(url + "name")
    print(resp.text)

def getToken():
    resp = requests.get(url + "getToken/" + str(MachineSettings['MachineID'])) 
    try:
        JToken = resp.json()
        return JToken['token']
    except:
        return 0 # OK

def getOrder():
    resp = requests.get(url + "getOrder/" + str(MachineSettings['MachineID']))
    try:
        JToken = resp.json()
        return JToken
    except:
        return 0 # OK

def postTokenStatus(status):
    codeSmile = requests.post(url + "postTokenStatus/" + str(MachineSettings['MachineID']), json={"status": status})
    # return codeSmile
    # OK

def postOrderStatus(status):
    codeSmile = requests.post(url + "postOrderStatus/" + str(MachineSettings['MachineID']), json={"status": status})
    # return codeSmile   # optional (if you are in mood)
    # OK
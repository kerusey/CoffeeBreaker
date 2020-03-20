import json
import requests

MachineSettingsPath = "/home/pi/CoffeeBreaker/CoffeeMachine/MachineSettings.json"

with open(MachineSettingsPath) as json_file:
        MachineSettings = json.load(json_file)

url = "http://" + MachineSettings['Ip'] + ":" + str(MachineSettings['Port']) + "/" # <- insert here method path

def getHelloWorld():
    resp = requests.get(url)
    print(resp.text)


def getOrderStatus():
    resp = requests.get(url + "getOrderStatus/" + str(MachineSettings['MachineID']))
    try:
        JOrderStatus = resp.json()
        return JOrderStatus['status']
    except:
        return resp

def getTokenStatus():
    resp = requests.get(url + "getTokenStatus/" + str(MachineSettings['MachineID']))
    try:
        JTokenStatus = resp.json()
        return JTokenStatus['status']
    except:
        return resp # OK
    
def getToken():
    resp = requests.get(url + "getToken/" + str(MachineSettings['MachineID']))
    try:
        JToken = resp.json()
        return JToken['token']
    except:
        return resp # OK

def getOrder():
    resp = requests.get(url + "getOrder/" + str(MachineSettings['MachineID']))
    try:
        JOrder = resp.json()
        return JOrder
    except:
        return resp # OK

def postTokenStatus(status):
    codeSmile = requests.post(url + "postTokenStatus/" + str(MachineSettings['MachineID']), json={"status": status})
    # return codeSmile
    # OK

def postOrderStatus(status):
    codeSmile = requests.post(url + "postOrderStatus/" + str(MachineSettings['MachineID']), json={"status": status})
    # return codeSmile   # optional (if you are in mood)
    # OK

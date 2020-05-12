import json
import requests

scriptDir = os.path.dirname(os.path.abspath(__file__)) + "/"
MachineSettingsPath = scriptDir + "/MachineSettings.json"

with open(MachineSettingsPath) as json_file:
    MachineSettings = json.load(json_file)

url = "http://" + MachineSettings['Ip'] + ":" + str(MachineSettings['Port']) + "/" # <- insert here method path

def getHelloWorld():
    resp = requests.get(url)
    print(resp.text)

def getOrderStatus():
    resp = requests.get(url + "getOrderStatus/" + str(MachineSettings['MachineID']))
    return resp.text # OK

def getTokenStatus():
    resp = requests.get(url + "getTokenStatus/" + str(MachineSettings['MachineID']))
    return resp.text # OK
        
def getToken():
    resp = requests.get(url + "getToken/" + str(MachineSettings['MachineID']))
    return resp.text

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

def postToken(token):
    codeSmile = requests.post(url + "postToken/" + str(MachineSettings['MachineID']), json={"token": token})

def postOrderStatus(status):
    codeSmile = requests.post(url + "postOrderStatus/" + str(MachineSettings['MachineID']), json={"status": status})
    # return codeSmile   # optional (if you are in mood)
    # OK

def postOrder(JOrder):
    codeSmile = requests.post(url + "postOrder/" + str(MachineSettings['MachineID']), json=JOrder)

'''
JOrder = {  "machineId": 1,
            "coffeeType": "espresso",
            "strength": 5,      
            "volume": 1,
            "milk": False,
            "sugar": 1
         }
         
while(True):
    getTokenStatus()
'''

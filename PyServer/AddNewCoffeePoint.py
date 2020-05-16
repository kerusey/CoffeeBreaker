import getopt
import json
import os
import sys

helpmsg = "python3 AddNewCoffeePoint.py -name <CoffeePointName> -x <XCoordinate> -y <YCoordinate> -id <CoffeeClusterID>"
argumentList = (('name', '-n', '--name'), ('x', '-x', '--x'), ('y', '-y', '--y'), ('id', '-i', '--id'))

path = os.path.dirname(os.path.abspath(__file__)) + "/"

if (not os.path.isfile(path + "CoffeeHouses.json")):
    with open(path + "CoffeeHouses.json", "w") as jsonFile:
        json.dump({}, jsonFile)

try:
    options, arguments = getopt.getopt(sys.argv[1:], 'n:x:y:i:h', ['name=', 'x=', 'y=', 'id=', 'help'])
except getopt.GetoptError:
    print(helpmsg)
    exit()

currentLocation = {}

for option, argument in options:
    if (option in ('-h', '--help')):
        print (helpmsg)
        exit()

for index, item in enumerate(argumentList):
    if (options[index][0] in item):
        if (item[0] == "name"):
            currentLocation[item[0]] = options[index][1]
        elif (item[0] == "id"):
            currentLocation[item[0]] = int(options[index][1])
        else:
            currentLocation[item[0]] = float(options[index][1])

def formatDict(rawDict:dict):
    nameOfCurrentPoint = rawDict['name']
    del rawDict['name']
    return nameOfCurrentPoint, rawDict

feeds = json.load(open(path + "CoffeeHouses.json"))
with open(path + "CoffeeHouses.json", "w") as jsonFile:
    nameOfCurrentPoint, formattedDict = formatDict(currentLocation)
    print(formattedDict)
    feeds[nameOfCurrentPoint] = formattedDict
    json.dump(feeds, jsonFile, indent=4)
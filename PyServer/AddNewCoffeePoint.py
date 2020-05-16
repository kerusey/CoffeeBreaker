import sys
import json
import os
import getopt

path = os.path.dirname(os.path.abspath(__file__))+"/"
coffeeShopInformation={}

def _usage():
    print("AddNewCoffeePoint.py [-n|--name <>] [-x|--x <>] [-y|--y <>] [-id|--id <>]")

if __name__ == "__main__":
    argv = sys.argv[1:]
    try:
        opts, args = getopt.getopt(argv, "h", ["name=", "x=","y=", "id="])
    except getopt.GetoptError:
        _usage()
        sys.exit(2)

    if len(opts) == 0:
        _usage()
        sys.exit()

    for opt, arg in opts:
        if opt == '-h':
            _usage()
            sys.exit()
        elif opt in ("-x", "--x"):
            coffeeShopInformation["x"] = arg
        elif opt in ("-y", "--y"):
            coffeeShopInformation["y"] = arg
        elif opt in ("-id", "--id"):
            coffeeShopInformation["id"] = arg
        elif opt in ("-n", "--name"):
            coffeeShopInformation["name"] = arg
print(coffeeShopInformation)

sys.exit()
'''
def CoffeeHousesReading():#reading of json file
    with open(path+"CoffeeHouses.json","r") as cafesRead:
        info = json.load(cafesRead)
        for row in info:
            coffeeShopInformation[row] = info[row]

#sys.argv[1] number of arguments
position=0#number of added argument
skip=2
if (not sys.argv[1] is None and int(sys.argv[1])!=0):
    while (position<int(sys.argv[1])):
        CoffeeHousesReading()
        numbersOfCafes = len(coffeeShopInformation)+position+1#for number of added cafe
        coffeeShopInformation[numbersOfCafes]["name"] = sys.argv[skip]
        coffeeShopInformation[numbersOfCafes]["x"] = sys.argv[skip+1]
        coffeeShopInformation[numbersOfCafes]["y"] = sys.argv[skip+2]
        position += 1
        skip += 3
        with open(path+"CoffeeHouses.json","a") as cafesWrite:#writing of inputed information to json
            coffeeHousesJson = json.dumps(coffeeShopInformation)
            cafesWrite.write(coffeeHousesJson ,sort_keys=True, indent=4, separators=(',', ': '))
else: print("There is no args to add")'''
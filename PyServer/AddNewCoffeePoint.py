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
with open(path+"CoffeeHouses.json","a") as cafesWrite:#appending of inputed information to json
            coffeeHousesJson = json.dumps(coffeeShopInformation)
            cafesWrite.write(coffeeHousesJson ,sort_keys=True, indent=4, separators=(',', ': '))
sys.exit()
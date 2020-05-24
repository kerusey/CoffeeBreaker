path = os.path.dirname(os.path.abspath(__file__)) + "/"

class OrderFromApp(Resource):
    def post(self, id):
        parser = reqparse.RequestParser()
        parser.add_argument("coffeeType", type=str, location='json')
        for item in ingredients:
            if (item == "coffeeType"):
                continue
            parser.add_argument(item, type=int, location='json')
        args = parser.parse_args()
        requests.post('http://' + str(coffeeMachineClusterPool[id]) + ":" + str(port) + "/ToCluster", json=json.dumps(args))
        return 200

class DataToDataBase(Resource):
    def post(self):
        parser = reqparse.RequestParser()
        for name in dataBaseHeader:
            parser.add_argument(name, type=int, location='json')
        args = parser.parse_args()
        DataBaseInsertion.valueInsertion(args)
        DataBaseInsertion.printingBD()
        return 200

api.add_resource(OrderFromApp, "/post/OrderFromApp_<id>")
api.add_resource(DataToDataBase, "/post/ToDataBase")
app.run(host, port, debug=True, threaded=True)

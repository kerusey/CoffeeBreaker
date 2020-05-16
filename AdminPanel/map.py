from flask import Flask, render_template
from flask_googlemaps import GoogleMaps
from flask_googlemaps import Map
import json
import os

lat = 60.0042558
lng = 30.2549297

app = Flask(__name__, template_folder=".")
app.config['GOOGLEMAPS_KEY'] = "AIzaSyAVdwpb_rw11KBFDt91-Ac0uWu9Vj_9B5w"
GoogleMaps(app)

path = os.path.dirname(os.path.abspath(__file__)) + "/"

places = json.load(open(path + "CoffeeHouses.json"))

markers=[]

for item in places:
	markerTemplate = {
		'icon': 'http://maps.google.com/mapfiles/ms/icons/green-dot.png'
	}
	markerTemplate['lat'] = places[item]['x']
	markerTemplate['lng'] = places[item]['y']
	markerTemplate['infobox'] = "<b> " + item + " </b>"
	markers.append(markerTemplate)

@app.route("/")
def mapview():
	mymap = Map(
		identifier="view-side",
		lat=lat,
		lng=lng,
		markers=[(lat, lng)],
		maptype="HYBRID",
		style="height:550px;width:1300px;margin:0;"
	)
	sndmap = Map(
		identifier="sndmap",
		lat=lat,
		lng=lng,
		markers=markers,
		maptype="HYBRID",
		style="height:550px;width:1300px;margin:0;"
	)
	return render_template('example.html', mymap=mymap, sndmap=sndmap)

if __name__ == "__main__":
	app.run(debug=True)
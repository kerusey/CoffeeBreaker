from djgeojson.fields import PolygonField
from djgeojson.views import GeoJSONLayerView
from django.conf import settings
from django.conf.urls import url
from django.conf.urls.static import static
from django.db import models
from django.views.generic import TemplateView
from leaflet.forms.widgets import LeafletWidget
from django import forms
from django.contrib.gis.geos import Point, MultiPoint
from djgeojson.fields import PointField

multiPoint = [ ]	

for item in settings.COFFEE_HOUSES:
	casheDict = {
		"type": "Point",
		"coordinates": [
			settings.COFFEE_HOUSES[item]['x'], 
			settings.COFFEE_HOUSES[item]['y']
		]
	}
	multiPoint.append(casheDict)

class CoffeeBreakerSpot(models.Model):
	#name = models.CharField(max_length=256)
	#xCoord = models.FloatField()
	#yCoord = models.FloatField()
	#place = PolygonField()
	geom = PointField()

	
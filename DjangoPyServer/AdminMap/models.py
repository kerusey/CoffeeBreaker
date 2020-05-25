from djgeojson.fields import PolygonField
from djgeojson.views import GeoJSONLayerView
from django.conf import settings
from django.conf.urls import url
from django.conf.urls.static import static
from django.db import models
from django.views.generic import TemplateView
from leaflet.forms.widgets import LeafletWidget
from django import forms
from django.contrib.gis.db.models import PointField

class CoffeeBreakerSpot(models.Model):

	name = models.CharField(max_length=256)
	xCoord = models.TextField()
	yCoord = models.TextField() # FIXME
	geom = PolygonField()

	def __str__(self):
		return self.name

	@property
	def picture_url(self):
		return self.picture.url

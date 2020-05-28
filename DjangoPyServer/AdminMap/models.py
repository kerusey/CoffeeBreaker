from django.conf import settings
from django.db import models
from django.contrib.gis import forms
from djgeojson.fields import PointField

class CoffeeBreakerLocation(models.Model):
	name = models.CharField(max_length=255)
	xCoord = models.FloatField()
	yCoord = models.FloatField()
	coffeeID = models.IntegerField()
	# geom = PointField()

	def __str__(self):
		return self.name
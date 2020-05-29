from django.db import models

class CoffeeBreakerLocation(models.Model): # u may also write a full page and add it admin panel
	name = models.CharField(max_length=255)
	xCoord = models.FloatField()
	yCoord = models.FloatField()
	coffeeID = models.IntegerField()
	
	# js map with add-point method with callback to xCoord & yCoord fields

	def __str__(self):
		return self.name
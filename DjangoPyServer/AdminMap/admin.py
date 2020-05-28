from leaflet.admin import LeafletGeoAdmin
from django.contrib import admin
from .models import CoffeeBreakerLocation

admin.site.register(CoffeeBreakerLocation, LeafletGeoAdmin)

from leaflet.admin import LeafletGeoAdmin
from django.contrib import admin
from .models import CoffeeBreakerSpot as CBSpot
from django.db import models
from django_admin_display import admin_display

admin.site.register(CBSpot, LeafletGeoAdmin)

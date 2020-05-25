from leaflet.admin import LeafletGeoAdmin
from django.contrib import admin
from .models import CoffeeBreakerSpot as CBSpot
from django.db import models
from django_admin_display import admin_display

admin.site.register(CBSpot, LeafletGeoAdmin)

class NodeLocation(models.Model):
    @admin_display(
        short_description="Company owner",
        admin_order_field='owner__last_name',
    )
    def owner(self) -> bool:
        return self.owner.last_name

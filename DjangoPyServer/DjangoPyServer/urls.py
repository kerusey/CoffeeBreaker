from django.contrib import admin
from django.urls import include, path
from RestfulCoffeeBreaker import views
from django.views.generic import TemplateView
from AdminMap.models import CoffeeBreakerLocation
import os

urlpatterns = [
	path('admin/', admin.site.urls),
	path('', include('AdminMap.urls')),
	path('', include('RestfulCoffeeBreaker.urls'))
]

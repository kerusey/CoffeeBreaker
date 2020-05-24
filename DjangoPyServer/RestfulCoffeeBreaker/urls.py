from django.urls import path
from . import views

urlpatterns = [
	path('CoffeeHouses', views.getCoffeeHouses, name='CoffeeHouses')
]
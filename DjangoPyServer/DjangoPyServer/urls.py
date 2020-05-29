from django.contrib import admin
from django.urls import include, path
from AdminMap.models import CoffeeBreakerLocation

urlpatterns = [
	path('admin/', admin.site.urls),
	path('', include('AdminMap.urls')),
	path('', include('RestfulCoffeeBreaker.urls'))
]

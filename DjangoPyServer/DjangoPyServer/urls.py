from django.contrib import admin
from django.urls import include, path
from .views import *
from django.conf.urls import handler404, handler500

handler404 = error_404
handler500 = error_500

urlpatterns = [
	path('admin/', admin.site.urls),
	path('', include('AdminMap.urls')),
	path('', include('RestfulCoffeeBreaker.urls'))
]

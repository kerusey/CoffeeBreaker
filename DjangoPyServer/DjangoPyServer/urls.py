from django.contrib import admin
from django.urls import include, path
from . import views
from django.conf.urls import handler404, handler500

handler404 = views.error_404
handler500 = views.error_500

urlpatterns = [
	path('admin/', admin.site.urls),
	path('', include('AdminMap.urls')),
	path('', include('RestfulCoffeeBreaker.urls'))
]

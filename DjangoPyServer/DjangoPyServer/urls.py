from django.contrib import admin
from django.urls import include, path
from RestfulCoffeeBreaker import views

urlpatterns = [
	path('admin/', admin.site.urls),
    path('', include('RestfulCoffeeBreaker.urls')),

]

from django.urls import path
from . import views

urlpatterns = [
	path('get/CoffeeHouses/<str:typeof>', views.getCoffeeHouses, name='CoffeeHouses'),
	path('post/DataToDataBase/', views.postDataToDataBase, name='DataToDataBase'),
	path('post/OrderFromApp/<int:id>/', views.postOrderFromApp, name='OrderFromApp'),
]
from django.conf import settings
from django.shortcuts import render

def error_404(request, exception):
	data = {}
	return render(request, settings.TEMPLATE_SOURCE_DIR + '404.html', data)

def error_500(request):
	data = {}
	return render(request, settings.TEMPLATE_SOURCE_DIR + '500.html', data)
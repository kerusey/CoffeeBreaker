from django.conf import settings

from django.http import HttpResponse
from django.template import loader

def index(request):
	template = loader.get_template(settings.TEMPLATE_SOURCE_DIR + "index.html")
	return HttpResponse(template.render())
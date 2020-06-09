import os
import json

DEBUG = False

BASE_DIR = os.path.dirname(os.path.dirname(os.path.abspath(__file__))) + "/"
GLOBALS_DIR = BASE_DIR + "Globals/"
TEMPLATE_SOURCE_DIR = BASE_DIR + "templates/"

SECRET_KEY = open(GLOBALS_DIR + "SecretKey.txt").read()
DATA_BASE_CREDITS = json.load(open(GLOBALS_DIR + "DataBaseCredits.json"))
COFFEE_MACHINE_CLUSTER_POOL = json.load(open(GLOBALS_DIR + "CoffeeMachineClusterPool.json"))

ALLOWED_HOSTS = [
	"127.0.0.1",
	DATA_BASE_CREDITS['host'],
]

# Application definition

SITE_ID = 1

INSTALLED_APPS = [
	'leaflet',
	'djgeojson',
	'rest_framework',
	'RestfulCoffeeBreaker.apps.RestfulCoffeeBreakerConfig',
	'AdminMap.apps.AdminMapConfig',
	'django.contrib.sites',
	'django.contrib.admin',
	'django.contrib.auth',
	'django.contrib.contenttypes',
	'django.contrib.sessions',
	'django.contrib.messages',
	'django.contrib.staticfiles',
	'django.contrib.humanize',
	'django.contrib.gis'

]

MIDDLEWARE = [
	'django.middleware.security.SecurityMiddleware',
	'django.contrib.sessions.middleware.SessionMiddleware',
	'django.middleware.common.CommonMiddleware',
	'django.middleware.csrf.CsrfViewMiddleware',
	'django.contrib.auth.middleware.AuthenticationMiddleware',
	'django.contrib.messages.middleware.MessageMiddleware',
	'django.middleware.clickjacking.XFrameOptionsMiddleware',
]

ROOT_URLCONF = 'DjangoPyServer.urls'

TEMPLATES = [
	{
		'BACKEND': 'django.template.backends.django.DjangoTemplates',
		'DIRS': [
			os.path.join(BASE_DIR, 'templates'),
		],
		'APP_DIRS': True,
		'OPTIONS': {
			'context_processors': [
				'django.template.context_processors.debug',
				'django.template.context_processors.request',
				'django.contrib.auth.context_processors.auth',
				'django.contrib.messages.context_processors.messages',
			],
		},
	},
]

WSGI_APPLICATION = 'DjangoPyServer.wsgi.application'

REST_FRAMEWORK = {
	'DEFAULT_PERMISSION_CLASSES': [
		'rest_framework.permissions.DjangoModelPermissionsOrAnonReadOnly'
	]
}

DATABASES = {
	'default': {
		'ENGINE': 'django.contrib.gis.db.backends.mysql',
		'NAME': DATA_BASE_CREDITS['dataBaseName'],
		'HOST': DATA_BASE_CREDITS['host'],
		'USER': DATA_BASE_CREDITS['userName'],
		'PASSWORD': DATA_BASE_CREDITS['pass']
	}
}

STATICFILES_DIRS = [
	os.path.join(BASE_DIR, 'static/'),
	os.path.join(BASE_DIR, 'templates/'),
]

MEDIA_ROOT = os.path.join(BASE_DIR, 'media/')

STATIC_URL = '/static/'
MEDIA_URL = '/media/'

AUTH_PASSWORD_VALIDATORS = [
	{
		'NAME': 'django.contrib.auth.password_validation.UserAttributeSimilarityValidator',
	},
	{
		'NAME': 'django.contrib.auth.password_validation.MinimumLengthValidator',
	},
	{
		'NAME': 'django.contrib.auth.password_validation.CommonPasswordValidator',
	},
	{
		'NAME': 'django.contrib.auth.password_validation.NumericPasswordValidator',
	},
]

SERIALIZATION_MODULES = {
	"geojson": "django.contrib.gis.serializers.geojson",
}

# Internationalization
# https://docs.djangoproject.com/en/3.0/topics/i18n/

LANGUAGE_CODE = 'en-us'

TIME_ZONE = 'UTC'

USE_I18N = True

USE_L10N = True

USE_TZ = True

LEAFLET_CONFIG = {
	'DEFAULT_CENTER': (59.9386300, 30.3141300),
	'DEFAULT_ZOOM': 10,
	'MIN_ZOOM': 3,
	'MINIMAP': True,
	'MAX_ZOOM': 18,
	'TILES': 'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png'
}
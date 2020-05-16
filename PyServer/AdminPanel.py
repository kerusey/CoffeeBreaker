from flask import Flask
from flask_admin import Admin
import netifaces

def getLan(): # OK
    interfaces = netifaces.interfaces()
    for i in interfaces:
        if (i == 'lo'):
            continue
        iface = netifaces.ifaddresses(i).get(netifaces.AF_INET)
        if (iface != None):
            for j in iface:
                return str(j['addr'])

host = getLan()
app = Flask(__name__)
port = 8090

app.config['FLASK_ADMIN_SWATCH'] = 'cerulean'
admin = Admin(app, name='microblog', template_mode='bootstrap3')

app.run()
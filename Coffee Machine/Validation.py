import qrcode
import Visualizer
import Server

current_token = Visualizer.visualNewSession() #### initialize QRCode and save current token  

def serverCheck(token = current_token):
	if (Server.getToken() != token):
		return Visualizer.visualNewSession()
		# also app have to drop session => need feedback # FIXME
	else: # if (Server.getToken() == token)
		pass # app need another feedback to resume buyer session # FIXME
		return token


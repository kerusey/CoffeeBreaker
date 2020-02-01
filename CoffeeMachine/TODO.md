# TODO file 
____
## 1) response to the mobile app (Requests.py) ✅
 description:
 
 	1.1) if app token and current token are the same, send packet with response OK / CONTINUE / etc
  
 	1.2) if app token and current token are not the same, send packet with response DENIED / STOP / etc

___

## 2) write a python script that allaws you to download and upload file ✅
 description: 
 
 	2.1) get file link by reading specific folder (ex. "validation/ID/" + machineID + "validation.json") 
		
	2.2) download file by token via google drive python api (ex. if validation.json has a string("kjshfkjsfgy") as file token). Also download to specific folder (/home/pi/FTP/validation) 
	
	2.3) delete current file from drive and call serverCheck() func
	
	2.4) create a respond and post it to google drive
	
	RELEASED IN A DIFFERENT WAY
___

## 3) create architecture !!! ✅
 description:
 	
 	3) create scheme architecture
___

## 4) write, debud and test order.py ✅
 description:
 	
 	4.1) if scanned qr token is valid (1) and buyer has setted up his order, send it to FTP/order
 	
 	4.2) convert json to str and str to **data**, set up flag <bool>order_status = True (works fine)
 	
 	4.3) create new <barista> object with parameters(**data**)
 	
 	4.4) call <barista> object START method
___

## 5) debug Barista.py
 description:
 	
 	5.1) debug all methods and events of ButtonPressed 
 	
 	5.2) try real example
___

## 6) <RELEASED> write main file
 description:
 	
 	6.1) really last part of the project
 	
 	6.2) write the full logic over here (best idea to call funcs and methods from different files)
 	
 	6.3) coming soon
____

## 7) set ctrl + alt + x as a hotkey of maximizing window size
 description:
	
	7.1) set ctrl + alt + x as a hotkey in raspbian gui settings
____

## 8) create server ✅
	8.1) write http python server
	
___

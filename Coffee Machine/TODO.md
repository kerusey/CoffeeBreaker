# TODO file 
____
## 1) response to the mobile app (Validation.py) 
 description:
 
 	1.1) if app token and current token are the same, send packet with response OK / CONTINUE / etc
  
 	1.2) if app token and current token are not the same, send packet with response DENIED / STOP / etc

___

## 2) limit number of files that could be located in FTP/validation or FTP/order
 description: 
 
 	2.1) FTP/validation and FTP/order limits on ftprotocol interface
		
		2.2) shell exeption (deny access to write file if files number more than 0) 
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

## 6) <RELEASED> write main file
 description:
 	
 	6.1) really last part of the project
 	
 	6.2) write the full logic over here (best idea to call funcs and methods from different files)
 	
 	6.3) coming soon

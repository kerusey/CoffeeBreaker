// var fs = require('fs');
// var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;

// const mysql = require('mysql');
// var hostName="";

// fs.readFile("../Globals/DataBaseCredits.json", function(err, data){
//   if(err) console.log(err)
//   else {
//     var hostName = JSON.parse(data).host;
//     var request = new XMLHttpRequest();
//     request.open('GET', 'http://' + hostName + ':8000/get/CoffeeHouses/', true);
//     request.send();
//     request.onload = function() {
//     var data1 = JSON.parse(request.responseText);
// console.log(data1)
// for(el in data1){
//   console.log(el.replace(/_/gi, ' '));
// }

// }
//   }
// })

/*
const connection = mysql.createConnection({
  address: hostName,
  user: userName,
  password: pass,
  server: serverName,
  database: databaseName,
 });
 connection.connect(err =>{
    if(err){
      console.log(err);
    }
    else {
      console.log("Connected");
    }
 })

 let query = "SELECT * FROM AdminMap_coffeebreakerlocations";
 connection.query(query,(err, result, field) =>{
   console.log(result);
 })
 /*
 connection.end(err =>{
  if(err){
    console.log(err);
  }
  else {
    console.log("Success!");
  }
 })*/











 var fs = require('fs');
		var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;
		const mysql = require('mysql');
		var hostName="";

		fs.readFile("../Globals/DataBaseCredits.json", function(err, data){
			if(err){
				console.log(err);
			}
			else {
				var len = 0;
				var hostName = JSON.parse(data).host;
				var request = new XMLHttpRequest();
				request.open('GET', 'http://' + hostName + ':8000/get/CoffeeHouses/', true);
				request.send();
				request.onload = function() {
					var data1 = JSON.parse(request.responseText);
					console.log(data1);
					for(el in data1){
						console.log(el.replace(/_/gi, ' '));
						len++;
					}
				}
			}
		})

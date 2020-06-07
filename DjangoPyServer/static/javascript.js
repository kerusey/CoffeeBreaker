		/*import library fs to open file with hostName*/
		var fs = require('fs');

		/*import library xmlhttprequest to send get request*/
		 var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;

		function map_init_basic (map, options) {
			/*Adding new layer*/
			var osmLayer = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
				attribution: '&copy; <a href="copyright">Openstreetmap</a>'
			}).addTo(map);
			/*Adding new marker*/
			var marker1 = L.marker([55.702868, 37.530865], {})
			.bindPopup('текст')
			.addTo(map);

			var marker2 = L.marker([55.709868, 37.530865], {})
			.bindPopup('текст2')
			.addTo(map);

			var hostName = "";
			/*Openning the file*/
			fs.readFile("../Globals/DataBaseCredits.json", function(err, data){
				if(err){
					/*Outputting errors*/
					console.log(err);
				}
				else {
					var hostName = JSON.parse(data).host;
					var request = new XMLHttpRequest();
					/*Get request*/
					request.open('GET', 'http://' + hostName + ':8000/get/CoffeeHouses/js', true);
					request.send();
					request.onload = function() {
						/*Working with data from response*/
						var CoffeeHousesLocations  = JSON.parse(request.responseText);
						console.log(CoffeeHousesLocations );
					}
				}
			})
	}
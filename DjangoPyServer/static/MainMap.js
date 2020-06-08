		/*import library fs to open file with hostName*/
		var fs = require('fs');

		/*import library xmlhttprequest to send get request*/
		 var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;

		function map_init_basic (map, options) {
			/*Adding new layer*/
			L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
				minzoom: 4,
				maxzoom: 7,
				attribution: '&copy; <a href="copyright">Openstreetmap</a>'

			}).addTo(map);
			/*Adding new marker*/
			var iconOptions = {
				iconUrl: '../static/myIcon.png',
				iconSize: [30, 30]
			}
			var icon = L.icon(iconOptions);
			L.marker([55.702868, 37.530865], {icon: icon})
			.bindPopup('текст')
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
					var iconOptions = {
						iconUrl: 'myIcon.png',
						iconSize: [50, 50]
					}
					var icon = L.icon(iconOptions);
					/*Get request*/
					request.open('GET', 'http://' + hostName + ':8000/get/CoffeeHouses/js', true);
					request.send();
					request.onload = function() {
						/*Working with data from response*/
						var CoffeeHousesLocations  = JSON.parse(request.responseText);
						console.log(CoffeeHousesLocations );
						for(el in CoffeeHousesLocations){
							L.marker([el.x, el.y], {icon: icon})
							.bindPopup(el.name)
							.addTo(map);
						}
					}
				}
			})
	}

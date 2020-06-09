function map_init_basic (map, options) {
	/*Adding new layer*/
	L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
		attribution: '&copy; <a href="copyright">Openstreetmap</a>',
	}).addTo(map);

    var hostName = "chaos.fstudio.space";
	var iconOptions = {
		iconUrl: '../static/myIcon.png',
		iconSize: [30, 30]
	};
	var icon = L.icon(iconOptions);
    /*Get request*/
    var request = new XMLHttpRequest();
	request.open('GET', 'http://' + hostName + ':8000/get/CoffeeHouses/js', true);
	request.send();
	request.onload = function() {
		/*Working with data from response*/
		var CoffeeHousesLocations  = JSON.parse(request.responseText);
		console.log(CoffeeHousesLocations );
		Object.entries(CoffeeHousesLocations).forEach(([prop, val]) => {
			console.log(val.xCoord);
			L.marker([val.xCoord, val.yCoord], {icon: icon})
			.bindPopup(val.name)
			.addTo(map);
		})
	}
}

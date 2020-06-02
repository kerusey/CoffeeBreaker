var map = L.map('map', {
}).setView([55.702868, 37.530865], 10);

var osmLayer = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
  attribution: '&copy; <a href="copyright">Openstreetmap</a>'
}).addTo(map);

var marker1 = L.marker([55.702868, 37.530865], {
  // icon: greenIcon
  draggable: true,
})
.bindPopup('текст')
.addTo(map);
var marker2 = L.marker([55.709868, 37.530865], {
  // icon: greenIcon
  draggable: true,
})
.bindPopup('текст2')
.addTo(map);

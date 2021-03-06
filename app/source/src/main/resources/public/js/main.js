var map;
var markers = [];
var bounds;
var updater;
var box;
var fromMadrid = false;
var enableBox = false;

function clearMarkers() {
	for (var i = 0; i < markers.length; ++i)
		markers[i].setMap(null);
	markers = [];
}

function updateMarkers() {
	clearMarkers();
	var sw = bounds.getSouthWest();
	var ne = bounds.getNorthEast();
	var query = '/api/offices';
	if (fromMadrid) {
		query = '/api/offices/MADRID';
	}
	query += '?first=' + sw.lat() + ',' + sw.lng() + '&second=' + ne.lat() + ',' + ne.lng();
	$.getJSON(query, function(data) {
		$.each(data, function(index, feature) {
			var office = feature.properties;
			var marker = new google.maps.Marker({
				position : new google.maps.LatLng(feature.geometry.coordinates[1], feature.geometry.coordinates[0]),
				map : map,
				title : office.name
			});

			markers.push(marker);

			var infoWindow = new google.maps.InfoWindow({
				content : '<h3>' + office.name + '</h3><br/><img src="'
						+ office.pictures[0]
						+ '" width="150px"></img><br/><a href="' + office.url
						+ '" target="_blank">More info</a>'
			});
			google.maps.event.addListener(marker, 'click', function() {
				infoWindow.open(map, marker);
			});
		});
	});
}

function addButton(map, title, tooltip, callback) {

	var controlDiv = document.createElement('div');
	controlDiv.style.padding = '5px';

	var controlUI = document.createElement('div');
	controlUI.style.backgroundColor = 'white';
	controlUI.style.borderStyle = 'solid';
	controlUI.style.borderWidth = '2px';
	controlUI.style.cursor = 'pointer';
	controlUI.style.textAlign = 'center';
	controlUI.title = tooltip;
	controlDiv.appendChild(controlUI);

	var controlText = document.createElement('div');
	controlText.style.fontFamily = 'Arial,sans-serif';
	controlText.style.fontSize = '12px';
	controlText.style.paddingLeft = '4px';
	controlText.style.paddingRight = '4px';
	controlText.innerHTML = '<b>' + title + '</b>';
	controlUI.appendChild(controlText);

	google.maps.event.addDomListener(controlUI, 'click', callback);

	controlDiv.index = 1;
	map.controls[google.maps.ControlPosition.TOP_RIGHT].push(controlDiv);
}

function updateMap() {
	bounds = enableBox ? box.getBounds() : map.getBounds();
	clearTimeout(updater);
	updater = setTimeout(updateMarkers, 100);
}

function initialize() {
	var mapOptions = {
		center : new google.maps.LatLng(40.41669, -3.700346),
		zoom : 6,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	};
	map = new google.maps.Map(document.getElementById('map_canvas'), mapOptions);

	addButton(map, "Todas", "Todas las oficinas", function() {
		fromMadrid = false;
		updateMap();
	});
	
	addButton(map, "Madrid", "Todas las oficinas de Madrid", function() {
		fromMadrid = true;
		updateMap();
	});
	
	addButton(map, "Caja", "Activar / desactivar la caja de inclusión", function() {
		box.setVisible(enableBox = !enableBox);
		updateMap();
	});
	
	box = new google.maps.Rectangle({
		map: map,
		bounds: new google.maps.LatLngBounds(
			new google.maps.LatLng(40.41669, -3.700346),
			new google.maps.LatLng(41.41669, -2.700346)),
		draggable: true,
		strokeColor: '#0000FF',
		strokeOpacity: 0.8,
		strokeWeight: 2,
		fillColor: '#0000FF',
		fillOpacity: 0.35,
		visible: false
	});
	
	google.maps.event.addListener(map, 'bounds_changed', function() {
		updateMap();
	});
	
	google.maps.event.addListener(box, 'dragend', function() {
		updateMap();
	});
}

	   var map;  //map object
	   var geocoder;
       var markers = [];

        function initMap() {
        geocoder = new google.maps.Geocoder();
         //Initialize the map object
         map = new google.maps.Map(document.getElementById('map'), {center: {lat:36.0018764,lng:-78.9385708}, zoom: 13});
         var locations = [{title: "Location 1", location: {lat:36.0018764, lng:-78.9385708}},
                          {title: "Location 2", location: {lat:36.0118764, lng:-78.9385708}},
                          {title: "Location 3", location: {lat:36.0218764, lng:-78.9385708}},
                          {title: "Location 4", location: {lat:36.0318764, lng:-78.9385708}}];
            
            var largeInfoWindow = new google.maps.InfoWindow();
            console.log(locations);
            
            for(var i = 0; i < locations.length; i++){
                var marker = new google.maps.Marker({map: map, position: locations[i].location, title: locations[i].title});
                markers.push(marker); //can just add to array and increment size, kinda like ArrayList in Java!
                marker.addListener('click', function(){populateInfoWindow(this, largeInfoWindow)}); //populateInfoWindow() is a new function we will define
            } 
        }

        function populateInfoWindow(marker, infowindow){
                if(infowindow.marker != marker){
                    infowindow.marker = marker;
                    infowindow.setContent('<div>' + marker.title + '</div>');
                    infowindow.open(map, marker);
                    infowindow.addListener('closeclick', function(){
                        infowindow.setMarker(null);
                    });
                }
            }
          function distance(lat1, lon1, lat2, lon2) {
	       var radlat1 = Math.PI * lat1/180
	       var radlat2 = Math.PI * lat2/180
	       var theta = lon1-lon2
	       var radtheta = Math.PI * theta/180
	       var dist = Math.sin(radlat1) * Math.sin(radlat2) + Math.cos(radlat1) * Math.cos(radlat2) * Math.cos(radtheta);
	       dist = Math.acos(dist)
	       dist = dist * 180/Math.PI
	       dist = dist * 60 * 1.1515
	       return dist
        }

        function computeSchool(userLat,userLon,schoolLevel,minGrade) {
			var n = 3;
			var result = [];
			var distArray = new Array(names.length);
			for(i=0;i<names.length;i++){
				distArray[i] = distance(userLat,userLon,lat[i],lon[i]);
			}
			var distdummy = distArray.slice();
            distdummy.sort();
			var count = 0;
			for(i=0;i<names.length;i++){
				var schoolIndex = distArray.indexOf(distdummy[i]);
				if(type[schoolIndex] === schoolLevel && grade[schoolIndex].localeCompare(minGrade) <= 0){
					result.push(schoolIndex);
					count++;
				}
				if(count>=n){
					break;
				}
			}
		
			return result;
		}

		var test = computeSchool(35.9049165,-79.0491021,"Elementary","F");
		alert(test);
var map;
let centerLatitude = 37.85;
let centerLongitude = -97.65;
let centerZoom = 4;
let parkUrl = "https://developer.nps.gov/api/v1/parks?limit=465&api_key=ljfsoa6TcSZddUPBiKFw450uW1FKOU0N03N6Tsux";
let allParks = [];


function nationalParksRequest() {
    var request = new XMLHttpRequest();
    request.open('GET', parkUrl, false);
    request.send(null);
    var jsonParks = JSON.parse(request.responseText);
    for (let i = 0; i < jsonParks.data.length; i++) {
        allParks.push(jsonParks.data[i]);
    }
}

nationalParksRequest();
for (let i = 0; i < allParks.length; i++) {
    allParks[i].latLng = { lat: parseFloat(allParks[i].latitude), lng: parseFloat(allParks[i].longitude) };
}

function initMap() {

    const directionsService = new google.maps.DirectionsService();
    const directionsRenderer = new google.maps.DirectionsRenderer();
    const infoWindow = new google.maps.InfoWindow();

    const map = new google.maps.Map(document.getElementById("map"), {
        zoom: centerZoom,
        center: { lat: centerLatitude, lng: centerLongitude },
    });

    directionsRenderer.setMap(map);
    getAutocompleteData();

    const onChangeHandler = function () {
        calculateAndDisplayRoute(directionsService, directionsRenderer);
    };

    document.querySelector("#submit-button").addEventListener("click", onChangeHandler);
      document.querySelector("#reset-button").addEventListener("click", function(){

                 location.reload();
                 console.log("reset button clicked");
                 });


    function displayMarkerAndInfoWindow(place) {
      let marker = new google.maps.Marker({
        map: map,
        position: place.latLng,
        title: place.fullName,
      });
      let infoWindowDefaultText = "point of interest";
      let infoWindowMarkerText = "<img src =" + `${place.images[0].url}` + " width='500'><br><br><b>"+`${place.fullName}`+"</b>" + "<br>" + `${place.description}` +
      "<br><br>Park Designation: " + `${place.designation}` + "<br><a href=" + `${place.directionsUrl}` + "/>" + `${place.directionsUrl}` ;

      marker.addListener("click", () => {
        infoWindow.setContent(infoWindowMarkerText || infoWindowDefaultText);
        infoWindow.open({
            anchor: marker,
            map,
        });
      });
      return marker;
    }

    function stopEnterFromSubmittingForm(elementId) {
        google.maps.event.addDomListener(elementId, "keydown", function(event) {
            if (event.keyCode === 13){
                event.preventDefault();
            }
        });
    }

    function getAutocompleteData() {
        autocompleteRequest =
        {
            componentRestrictions: {'country': ['us']},
            fields: ['geometry', 'name', 'formatted_address']
        }

        var originInput = document.getElementById("originInput");
        var origin = new google.maps.places.Autocomplete(originInput, autocompleteRequest);
        var destinationInput = document.getElementById("destinationInput");
        var destination = new google.maps.places.Autocomplete(destinationInput, autocompleteRequest);

        new google.maps.places.Autocomplete(document.getElementById("stopOne"), autocompleteRequest);
        new google.maps.places.Autocomplete(document.getElementById("stopTwo"), autocompleteRequest);
        new google.maps.places.Autocomplete(document.getElementById("stopThree"), autocompleteRequest);


        stopEnterFromSubmittingForm(originInput);
        stopEnterFromSubmittingForm(destinationInput);
        stopEnterFromSubmittingForm(document.getElementById("stopOne"));
        stopEnterFromSubmittingForm(document.getElementById("stopTwo"));
        stopEnterFromSubmittingForm(document.getElementById("stopThree"));
    }

    function calculateAndDisplayRoute(directionsService, directionsRenderer) {
        var request = {
            origin: document.getElementById("originInput").value,
            destination: document.getElementById("destinationInput").value,
            travelMode: google.maps.TravelMode.DRIVING,
            unitSystem: google.maps.UnitSystem.IMPERIAL
        }
        directionsService.route(request)
        .then((response) => {
            directionsRenderer.setDirections(response);
            findPointsOfInterest(response.routes[0].overview_polyline, map);
        })
        .catch((e) => window.alert("Sorry, we could not calculate driving directions for these locations. Please try a different location."));
    }

    function findPointsOfInterest (encodedWaypoints, map) {

        let decodedWaypoints = decode(encodedWaypoints);
        let waypoints = [];

        for (let i = 0; i < decodedWaypoints.length; i+=8) {
            waypoints.push(decodedWaypoints[i]);
        }

        let allCircles = [];
        for(const waypoint of waypoints) {
            var waypointLatLng = new google.maps.LatLng(waypoint[0], waypoint[1])
            var waypointCircle = new google.maps.Circle({
                strokeColor: "#add8e6",
                    strokeOpacity: 0,
                    strokeWeight: 0,
                    fillOpacity: 0.0,
                    map,
                    center: waypointLatLng,
                    radius: 160000,
            });
            allCircles.push(waypointCircle);
        }

        for (let i = 0; i < allParks.length; i++) {
            for (waypointCircle of allCircles) {
                if (google.maps.geometry.spherical.computeDistanceBetween(allParks[i].latLng, waypointCircle.getCenter()) <= waypointCircle.getRadius()) {
                    displayMarkerAndInfoWindow(allParks[i]);
                }
            }
        }
    }

}


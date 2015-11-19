/**
 * Created by Marcin Surowiec (msurowiec@lookatmycode.net) on 2015-10-17.
 */
var map = angular.module('map', ['leaflet-directive']);

map.controller('mapController', ['$scope', '$http', function($scope, $html) {
    angular.extend($scope, {
        map: {
            lat: 52.253195,
            lng: 20.899400,
            zoom: 17
        }
    });
}]);

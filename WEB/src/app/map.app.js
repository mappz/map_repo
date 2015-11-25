var map = angular.module('map', ['leaflet-directive', 'ngRoute', 'messagesModule','loginModule','firebase']);

map.config(['$routeProvider', function($routeProvider) {
    $routeProvider.otherwise({
        redirectTo: '/'
    });
}]);
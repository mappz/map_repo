var map = angular.module('map', ['leaflet-directive', 'ngRoute', 'messagesModule','loginModule','registrationModule','toastr','firebase']);

map.config(['$routeProvider', function($routeProvider) {
    $routeProvider.otherwise({
        redirectTo: '/'
    });
}]);
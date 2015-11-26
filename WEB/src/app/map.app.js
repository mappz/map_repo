var map = angular.module('map', ['leaflet-directive', 'ngRoute', 'messagesModule', 'loginModule', 'firebase', 'authService', 'registrationModule', 'toastr']);

map.config(['$routeProvider', function($routeProvider) {
    $routeProvider.otherwise({
        redirectTo: '/'
    });
}]);

map.run(["$rootScope", "$location", function($rootScope, $location) {
    $rootScope.$on("$routeChangeError", function(event, next, previous, error) {
        // We can catch the error thrown when the $requireAuth promise is rejected
        // and redirect the user back to the home page
        console.log("catched")
        if (error === "AUTH_REQUIRED") {
            $location.path("/login");
        }
    });
}]);
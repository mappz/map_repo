var map = angular.module('map', ['leaflet-directive','ui.bootstrap' ,'ngAnimate', 'ngRoute', 'messagesModule', 'loginModule', 'firebase', 'authService', 'registrationModule', 'notificationsModule', 'settingsModule', 'toastr', 'ngCookies', 'fireService']);

map.config(['$routeProvider', function($routeProvider) {
    $routeProvider.otherwise({
        redirectTo: '/'
    });
}]);

map.run(["$rootScope", "$location", 'toastr', function($rootScope, $location, toastr) {
    $rootScope.$on("$routeChangeError", function(event, next, previous, error) {
        // We can catch the error thrown when the $requireAuth promise is rejected
        // and redirect the user back to the home page
        console.log("catched")
        if (error === "AUTH_REQUIRED") {
            toastr.warning("You are not logged in")
            $location.path("/login");
        }
    });
}]);
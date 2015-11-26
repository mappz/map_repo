var registrationModule = angular.module("registrationModule", []);

registrationModule.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/register', {
        templateUrl: 'app/components/registration/registration.template.html',
        controller: 'registrationController'
    })
}]);
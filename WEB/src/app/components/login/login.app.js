var loginModule = angular.module("loginModule",[]);

loginModule.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/login', {
        templateUrl: 'app/components/login/login.template.html',
        controller: 'loginController'
    })
}]);
var settingsModule = angular.module("settingsModule", []);
settingsModule.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/settings', {
        templateUrl: 'app/components/settings/settings.template.html',
        controller: 'settingsController',
        resolve: {
            "currentAuth": ["Auth", function(Auth) {
                console.log(Auth)
                return Auth.$requireAuth();
            }]
        }
    })
}]);
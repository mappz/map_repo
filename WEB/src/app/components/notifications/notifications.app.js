var notificationsModule = angular.module("notificationsModule", [])
notificationsModule.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/notifications', {
        templateUrl: 'app/components/notifications/notifications.template.html',
        controller: 'notificationsController',
        resolve: {
            "currentAuth": ["Auth", function(Auth) {
                return Auth.$requireAuth();
            }]
        }
    })
}]);
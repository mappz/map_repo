var messagesModule = angular.module('messagesModule', ['leaflet-directive','messageDirective']);

messagesModule.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/', {
        templateUrl: 'app/components/messages/messages.template.html',
        controller: 'messagesController',
        resolve: {
            "currentAuth": ["Auth", function(Auth) {
                return Auth.$requireAuth();
            }]
        }
    })
}]);
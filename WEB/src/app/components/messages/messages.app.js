var messagesModule = angular.module('messagesModule', ['leaflet-directive']);

messagesModule.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/', {
        templateUrl: 'app/components/messages/messages.template.html',
        controller: 'messagesController',
         resolve: {
            "currentAuth": ["Auth", function(Auth) {
                console.log("Zaloguj sie");
                JSON.stringify(Auth);
              return Auth.$waitForAuth();
            }]
        }
    })
}]);
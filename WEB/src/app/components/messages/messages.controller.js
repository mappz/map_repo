var messagesModule = angular.module("messagesModule");
messagesModule.controller('messagesController', ['$scope', '$http', function($scope, $http) {
    angular.extend($scope, {
        map: {
            lat: 52.253195,
            lng: 20.899400,
            zoom: 17
        }
    });
}]);
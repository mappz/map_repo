var messagesModule = angular.module("messagesModule");
messagesModule.controller('messagesController', ['$scope', '$http','currentAuth', function($scope, $http,currentAuth) {
    angular.extend($scope, {
        map: {
            lat: 52.253195,
            lng: 20.899400,
            zoom: 17
        }
    });
}]);
var loginModule = angular.module("loginModule");

loginModule.controller("loginController", ["$scope","$http",function($scope,$http){
 var ref = new Firebase("https://dazzling-fire-990.firebaseio.com");

  $scope.login = function() {
     ref.authWithPassword({
       email    : $scope.user.login,
       password : $scope.user.password
     }, function(error, authData) {
       if (error) {
         console.log("Login Failed!", error);
       } else {
         console.log("Authenticated successfully with payload:", authData);
       }
     });
     }
}]);

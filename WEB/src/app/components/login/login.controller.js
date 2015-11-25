var loginModule = angular.module("loginModule");

loginModule.controller("loginController", ["$scope","$http", "toastr", function($scope,$http,toastr){
 var ref = new Firebase("https://dazzling-fire-990.firebaseio.com");

  $scope.login = function() {
     ref.authWithPassword({
       email    : $scope.user.login,
       password : $scope.user.password
     }, function(error, authData) {
       if (error) {
         toastr.error("Niepoprawne dane logowania!");
       } else {
         toastr.success("Zalogowano pomyślnie!");
       }
     });
     }
}]);

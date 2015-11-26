var loginModule = angular.module("loginModule");

loginModule.controller("loginController", ["$scope", "$http", "toastr", '$cookies','fire', function($scope, $http, toastr, $cookies, Fire) {
    var ref = Fire.ref;

    $scope.login = function() {
        ref.authWithPassword({
            email: $scope.user.login,
            password: $scope.user.password
        }, function(error, authData) {
            if (error) {
                toastr.error("Niepoprawne dane logowania!");
            } else {
                console.log(authData)
                console.log("Creating cookies")
                $cookies.putObject('user', {
                    email: $scope.user.login,
                    nick: $scope.user.login.split("@")[0],
                    img: authData.password.profileImageURL
                });
                console.log("cookie created")
                toastr.success("Zalogowano pomyślnie!");
            }
        });
    }
}]);
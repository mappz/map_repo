var loginModule = angular.module("loginModule");

loginModule.controller("loginController", ["$scope", "$http", "toastr", '$cookies', 'fire', '$location', function($scope, $http, toastr, $cookies, Fire, $location) {
    var ref = Fire.ref;

    $scope.login = function() {
        ref.authWithPassword({
            email: $scope.user.login,
            password: $scope.user.password
        }, function(error, authData) {
            if (error) {
                toastr.error("User or password are incorrect");
            } else {
                console.log(authData)
                console.log("Creating cookies")
                $cookies.putObject('user', {
                    email: $scope.user.login,
                    nick: $scope.user.login.split("@")[0],
                    img: authData.password.profileImageURL,
                    uid: authData.uid
                });
                console.log("cookie created")
                toastr.success("Logged in");
                $location.path("/");
            }
        }, {
            remember: "sessionOnly"
        });
    }
}]);
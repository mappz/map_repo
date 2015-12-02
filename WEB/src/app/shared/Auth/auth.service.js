var authService = angular.module("authService", [])
authService.factory("Auth", ["$firebaseAuth", "fire",
    function($firebaseAuth, Fire) {
        var ref = Fire.ref;
        return $firebaseAuth(ref);
    }
]);

authService.controller("authController", ['$scope', '$rootScope', 'Auth', '$location', 'fire', '$cookies', 'toastr', function($scope, $rootScope, Auth, $location, Fire, $cookies, toastr) {
    $rootScope.$on('$locationChangeSuccess', function(event) {
        $scope.auth = Auth.$getAuth();
    })

    $scope.logout = function() {
        Fire.ref.unauth();
        $cookies.remove("user");
        toastr.warning("Logged out !")
        $location.path("/login")
    }
}])
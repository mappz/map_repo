var authService = angular.module("authService", [])
authService.factory("Auth", ["$firebaseAuth","fire",
    function($firebaseAuth, Fire) {
        var ref = Fire.ref;
        return $firebaseAuth(ref);
    }
]);
authService.controller("authController", ['Auth',function(Auth){
console.log(Auth);
}])
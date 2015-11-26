var authService = angular.module("authService", [])
authService.factory("Auth", ["$firebaseAuth",
    function($firebaseAuth) {
        var ref = new Firebase("https://dazzling-fire-990.firebaseio.com");
        return $firebaseAuth(ref);
    }
]);
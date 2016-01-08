var loginModule = angular.module("loginModule");


loginModule.controller("loginController", ["$scope", "$http", "toastr", '$cookies', 'fire', '$location', function($scope, $http, toastr, $cookies, Fire, $location) {
    var ref = Fire.ref;

    var users = [];

    Fire.userEx.on("child_added", function(snapshot, prevChildKey) {
        console.log("got userEx");

        console.log(snapshot.val());

        users.push(snapshot.val());
    });

/**
 * Login user
 * @returns {undefined} nothing
 */
    $scope.login = function() {
        ref.authWithPassword({
            email: $scope.user.login,
            password: $scope.user.password
        }, function(error, authData) {
            if (error) {
                toastr.error("User or password are incorrect");
            } else {

                console.log("got authData");

                console.log(authData);

                console.log("searching for uid=" + authData.uid);

                var userEntry = null;

                console.log("users.length=" + users.length);

                for (var i = 0; i < users.length && !userEntry; i++) {
                    if (users[i].uid == authData.uid)
                        userEntry = users[i];
                }

                if (!userEntry) {
                    console.log("Attempt to find user uid=" + authData.uid + " failed!");
                    return;
                }

                console.log("Creating cookies")
                $cookies.putObject('user', {
                    email: $scope.user.login,
                    nick: $scope.user.login.split("@")[0],
                    img: authData.password.profileImageURL,
                    uid: authData.uid,
                    ex: userEntry
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
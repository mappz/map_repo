var registrationModule = angular.module("registrationModule")

registrationModule.controller('registrationController', ['$scope', '$http', 'toastr', 'Auth', 'fire', function($scope, $http, toastr, Auth, Fire) {

    $scope.calendarStatus = false;
    $scope.open = function($event) {
        $scope.calendarStatus = true;
    };
    $scope.categoriesList = [{
        name: 'Sport',
        val: 'sport',
    }, {
        name: 'Party',
        val: 'party',
    }, {
        name: 'Trades',
        val: 'trades',
    }, {
        name: 'Meet',
        val: 'meet',
    }]
    $scope.createUser = function() {
        $scope.message = null;
        $scope.error = null;


        if ($scope.checkPassword != $scope.password) {
            toastr.error("Podane hasła nie pasują do siebie!");
            return;
        }

        Auth.$createUser({
            email: $scope.email,
            password: $scope.password
        }).then(function(userData) {
            console.log(userData);
            Fire.ref.authWithPassword({
                email: $scope.email,
                password: $scope.password
            }, function(error, authData) {
                if (error) {
                    // wrong
                } else {
                    var subscribedCat = [];
                    angular.forEach($scope.categoriesList, function(value, key) {
                        if (value.selected) {
                            subscribedCat.push(value.name);
                        }
                    });
                    Fire.userEx.push({
                        uid: authData.uid,
                        categories: subscribedCat,
                        firstName: $scope.firstName,
                        lastName: $scope.lastName,
                        birthDate: $scope.birthDate.toLocaleString(),
                        sex: $scope.sex,
                        role: 'USER'
                    })
                        toastr.success("Zostałeś zarejestrowany pomyślnie!");
                        Fire.ref.unauth();

                }
            })
        }).catch(function(error) {
            toastr.error("Wystąpił błąd: " + error);
        });
    };
}]);
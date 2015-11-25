var registrationModule = angular.module("registrationModule")

registrationModule.controller('registrationController', ['$scope', '$http', 'toastr', 'Auth', function($scope, $http, toastr, Auth) {
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
            toastr.success("Zostałeś zarejestrowany pomyślnie!");
        }).catch(function(error) {
            toastr.error("Wystąpił błąd: " + error);
        });
    };
}]);
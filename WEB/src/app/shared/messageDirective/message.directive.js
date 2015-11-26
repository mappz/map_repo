var messageDirectives = angular.module('messageDirective', []);
messageDirectives.directive("conversationPopup", ['$cookies', 'toastr', function($cookies, toastr) {
    return {
        restrict: 'E',
        scope: {
            conversation: '='
        },
        templateUrl: 'app/shared/messageDirective/conversation-popup.template.html',
        controller: ['$scope', '$element', '$attrs', '$transclude', function($scope, $element, $attrs, $transclude) {
            console.log("Hello");
            console.log($scope.conversation)
            $scope.send = function() {
                var user = $cookies.getObject('user');
                if (user != null) {
                    var ref = new Firebase("https://dazzling-fire-990.firebaseio.com");
                    var webMessages = ref.child("webMessages");
                    webMessages.push({
                        talkId: $scope.conversation.talkId,
                        author: user.nick,
                        content: $scope.content,
                        date: new Date().toLocaleString(),
                        latitude: $scope.conversation.latitude,
                        longtitude: $scope.conversation.longtitude,
                        img: user.img
                    });
                    toastr.success("Nie można wysłać wiadomości")
                } else {
                    toastr.error("Nie można wysłać wiadomości")
                }
            }
        }],
    };
}]);
var messageDirectives = angular.module('messageDirective', []);
messageDirectives.directive("conversationPopup", ['$cookies', 'toastr','fire', function($cookies, toastr,Fire) {
    return {
        restrict: 'E',
        scope: {
            conversation: '='
        },
        templateUrl: 'app/shared/messageDirective/conversation-popup.template.html',
        controller: ['$scope', '$element', '$attrs', '$transclude','$timeout', function($scope, $element, $attrs, $transclude,$timeout) {
            console.log("Hello");
            console.log($scope.conversation)
            $scope.send = function() {
                var user = $cookies.getObject('user');
                if (user != null) {
                    var webMessages = Fire.messages;
                    webMessages.push({
                        talkId: $scope.conversation.talkId,
                        author: user.nick,
                        content: $scope.content,
                        date: new Date().toLocaleString(),
                        latitude: $scope.conversation.latitude,
                        longtitude: $scope.conversation.longtitude,
                        img: user.img
                    });
		$scope.content="";	
$timeout(function(){
$(".messages-wrapper").scrollTop($(".messages-wrapper")[0].scrollHeight);
},0);
                } else {
                    toastr.error("Nie można wysłać wiadomości")
                }
            }
        }],
    };
}]);

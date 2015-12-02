var messageDirectives = angular.module('messageDirective', []);
messageDirectives.directive("conversationPopup", ['$cookies', 'toastr', 'fire', '$filter', 'moment', 'conversationFactory', 'Auth', function($cookies, toastr, Fire, $filter, moment, conversationFactory, Auth) {
    return {
        restrict: 'E',
        scope: {
            conversations: '=',
            key: '@'
        },
        templateUrl: 'app/shared/messageDirective/conversation-popup.template.html',
        controller: ['$scope', '$element', '$attrs', '$transclude', '$timeout', function($scope, $element, $attrs, $transclude, $timeout) {
            console.log("My key is:" + $scope.key)

            var id = $scope.conversations.$indexFor($scope.key);
            $scope.conversation = $scope.conversations[id];
            $scope.isAuthor = false;
            console.log("auth obj")
            $scope.authUid = Auth.$getAuth().uid;
            if ($scope.authUid === $scope.conversation.author.uid) {
                $scope.isAuthor = true;
            }

            $scope.send = function() {
                var user = $cookies.getObject('user');
                var author = conversationFactory.createAuthorModel(user.uid, user.nick, user.img);
                var message = conversationFactory.createMessage(author, $scope.content);
                conversationFactory.addMessageToConversation($scope.conversation, message);
                $scope.conversations.$save($scope.conversations[id]);
                $scope.content = "";
                $timeout(function() {
                    $(".messages-wrapper").scrollTop($(".messages-wrapper")[0].scrollHeight);
                }, 0);
            }
            $scope.removeConversation = function() {
                $scope.conversations.$remove(id).then(function() {
                    toastr.success("Conversation deleted");
                })
            }
            $scope.saveEditedMessage = function(data, val) {
                console.log("Saving edited message" + val + " and " + data)
                $scope.conversation.messages[val]['edited'] = true;
                $scope.conversation.messages[val].date = new Date().toLocaleString();
                $scope.conversations.$save($scope.conversations[id]);
            }
        }],
    };
}]);
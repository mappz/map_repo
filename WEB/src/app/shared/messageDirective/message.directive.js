var messageDirectives = angular.module('messageDirective', []);
/**
 * Message directive
 * @returns {undefined} nothing
 */
 var conversationDirective = function($cookies, toastr, Fire, $filter, moment, conversationFactory, Auth) {
    return {
        restrict: 'E',
        scope: {
            conversations: '=',
            key: '@',
            markers: '@'
        },
        templateUrl: 'app/shared/messageDirective/conversation-popup.template.html',
        controller: ['$scope', '$element', '$attrs', '$transclude', '$timeout', function($scope, $element, $attrs, $transclude, $timeout) {
            console.log("My key is:" + $scope.key)

            var id = $scope.conversations.$indexFor($scope.key);
            $scope.conversation = $scope.conversations[id];
            $scope.isAuthor = false;
            console.log("auth obj")
            $scope.authUid = Auth.$getAuth().uid;
            $scope.isAdmin = $cookies.getObject('user').ex.role == "ADMIN";

            console.log($cookies.getObject('user').ex.role);
            if ($scope.authUid === $scope.conversation.author.uid) {
                $scope.isAuthor = true;
            }


/**
 * Sends a message to Firebase and scrolls message box
 * @returns {undefined} nothing
 */
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
/**
 * Removes convarsation from firebase
 * @returns {undefined} nothing
 */
            $scope.removeConversation = function() {
                $scope.conversations.$remove(id).then(function() {
                    toastr.success("Conversation deleted");
                })
            }
/**
 * Saves edited message
 * @returns {undefined} nothing
 */
            $scope.saveEditedMessage = function(data, val) {
                console.log("Saving edited message" + val + " and " + data)
                $scope.conversation.messages[val]['edited'] = true;
                $scope.conversation.messages[val].date = new Date().toLocaleString();
                $scope.conversations.$save($scope.conversations[id]);
            }

/**
 * Removes messege
 * @returns {undefined} nothing
 */
            $scope.removeMessage = function(index) {
                console.log("Removing message " +  $scope.conversations[id].messages[index]);

                $scope.conversations[id].messages.splice(index, 1);
                $scope.conversations.$save($scope.conversations[id]).then(function() {
                    toastr.success("Message removed");
                });
            }
        }],
    };
};
messageDirectives.directive("conversationPopup", ['$cookies', 'toastr', 'fire', '$filter', 'moment', 'conversationFactory', 'Auth', conversationDirective]);

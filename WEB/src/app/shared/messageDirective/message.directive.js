var messageDirectives = angular.module('messageDirective', []);
messageDirectives.directive("conversationPopup", function(){
  return {
    restrict: 'E',
    scope: {
      conversation: '='
    },
    templateUrl: 'app/shared/messageDirective/conversation-popup.template.html',
    controller: function($scope, $element, $attrs, $transclude) {
       console.log("Hello");
       console.log($scope.conversation)
       $scope.send = function(){
         console.log("send message" + $scope.content);
       }
    },
  };
});
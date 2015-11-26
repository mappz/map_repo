var messagesModule = angular.module("messagesModule");
messagesModule.controller('messagesController', ['$scope', '$http','currentAuth', function($scope, $http,currentAuth) {
    angular.extend($scope, {
        map: {
            lat: 52.253195,
            lng: 20.899400,
            zoom: 17
        }
    });

    $scope.conversations = [];

    //Odbieranie wszystkich
    var getAll = function() {
        var ref = new Firebase("https://dazzling-fire-990.firebaseio.com/webMessages");

        var hashMap = new HashMap();

        ref.once("value", function(data) {
            var tmp = data.val();

            angular.forEach(tmp, function(value, key) {
                if (!hashMap.has(value.talkId)) {
                    hashMap.set(value.talkId, {talkId: value.talkId, latitude: value.latitude, longtitude: value.longtitude, messages: []});
                }

                hashMap.get(value.talkId).messages.push(value);
            });

            hashMap.forEach(function(value, key) {
                $scope.conversations.push(value);
            });

            console.log($scope.conversations);
        });
    }

    getAll();

    //Wysyłanie
    $scope.sendMessage = function() {
        var ref = new Firebase("https://dazzling-fire-990.firebaseio.com");

        var webMessages = ref.child("webMessages");

        webMessages.push({
         talkId: '1',
         author: 'Andrzej Stasiak',
         content: 'Przykładowy content...',
         date: '26-11-2015',
         latitude: 52.253195,
         longtitude: 20.899400
       });
    }

    $scope.sendMessage();


    //Odbieranie
    var startListening = function() {
        var ref = new Firebase("https://dazzling-fire-990.firebaseio.com");
        var webMessages = ref.child("webMessages");

        webMessages.on("child_added", function(snapshot, prevChildKey) {
          var message = snapshot.val();
          /*console.log("Author: " + message.author);
          console.log("Content: " + message.content);
          console.log("Talk ID: " + message.talkId);*/
        });
    }

    startListening();

}]);
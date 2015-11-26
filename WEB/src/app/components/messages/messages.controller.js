var messagesModule = angular.module("messagesModule");
messagesModule.controller('messagesController', ['$scope', '$http', 'currentAuth','$cookies', function($scope, $http, currentAuth,$cookies) {
    angular.extend($scope, {
        map: {
            lat: 52.253195,
            lng: 20.899400,
            zoom: 17
        },
    });

    $scope.markers = new Array();
    $scope.conversations = [];
    $scope.hashMap = new HashMap();

    var updateConversations = function() {
        $scope.conversations = [];
        $scope.hashMap.forEach(function(value, key) {
            $scope.conversations.push(value);
        });
    }

    var addToHashMap = function(value) {
        if (!$scope.hashMap.has(value.talkId)) {
            $scope.hashMap.set(value.talkId, {
                talkId: value.talkId,
                latitude: value.latitude,
                longtitude: value.longtitude,
                messages: []
            });
        }
                        if(value.content!=='<EMPTY>'){
                            $scope.hashMap.get(value.talkId).messages.push(value);
                        }
    }

    //Odbieranie wszystkich
    var getAll = function() {
        var ref = new Firebase("https://dazzling-fire-990.firebaseio.com/webMessages");

        ref.once("value", function(data) {
            var tmp = data.val();

            angular.forEach(tmp, function(value, key) {
                addToHashMap(value);
            });

            console.log($scope.conversations);
            angular.forEach($scope.conversations, function(value, key) {
                console.log("key: " + key)
                $scope.markers.push({
                    lat: value.latitude,
                    lng: value.longtitude,
                    getMessageScope: function() {
                        return $scope;
                    },
                    message: '<conversation-popup conversation="conversations[' + key + ']"></conversation-popup>'
                });
            })
            updateConversations();
        });
    }

    getAll();

    //Wysyłanie
    $scope.sendMessage = function() {

    }

    //$scope.sendMessage();


    //Odbieranie
    var startListening = function() {
        var ref = new Firebase("https://dazzling-fire-990.firebaseio.com");
        var webMessages = ref.child("webMessages");

        webMessages.on("child_added", function(snapshot, prevChildKey) {
            var message = snapshot.val();

            console.log("get message" + message.content);
            addToHashMap(message);
           // $scope.$apply();

            updateConversations();
        });
    }

    startListening();

    function guid() {
        function _p8(s) {
            var p = (Math.random().toString(16) + "000000000").substr(2, 8);
            return s ? "-" + p.substr(0, 4) + "-" + p.substr(4, 4) : p;
        }
        return _p8() + _p8(true) + _p8(true) + _p8();
    }
    $scope.$on("leafletDirectiveMap.click", function(event, args) {
        var leafEvent = args.leafletEvent;

        console.log(leafEvent.latlng.lat + " -> " + leafEvent.latlng.lng)
        var ref = new Firebase("https://dazzling-fire-990.firebaseio.com");
        var webMessages = ref.child("webMessages");
       var user = $cookies.getObject('user');
                       if (user != null) {
                           var ref = new Firebase("https://dazzling-fire-990.firebaseio.com");
                           var webMessages = ref.child("webMessages");
                           webMessages.push({
                               talkId: guid(),
                               author: user.nick,
                               content: '<EMPTY>',
                               date: new Date().toLocaleString(),
                               latitude: leafEvent.latlng.lat,
                               longtitude: leafEvent.latlng.lng,
                               img: user.img
                           });
                       } else {
                           toastr.error("Nie można wysłać wiadomości")
                       }
    });

}]);
var messagesModule = angular.module("messagesModule");
messagesModule.controller('messagesController', ['$scope', '$http', 'currentAuth', '$cookies', 'toastr', '$timeout', 'leafletData', 'fire', 'leafletMarkersHelpers', '$firebaseArray', function($scope, $http, currentAuth, $cookies, toastr, $timeout, leafletData, Fire, leafletMarkersHelpers, $firebaseArray) {
    angular.extend($scope, {
        map: {
            lat: 52.253195,
            lng: 20.899400,
            zoom: 17
        },
        layers: {
            baselayers: {
                osm: {
                    name: 'OpenStreetMap',
                    type: 'xyz',
                    url: 'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png'
                }
            },
            overlays: {
                messages: {
                    name: "Real world data",
                    type: "markercluster",
                    visible: true
                }
            }
        }
    });

    $scope.$on('$destroy', function() {
        console.log("Reset marker groups")
        leafletMarkersHelpers.resetMarkerGroups();
        leafletData.getMap().then(function(map) {

        })
    });

    console.log("Messages controller ***********************************************")
    $scope.markers = new Array();
    $scope.conversations = $firebaseArray(Fire.conversations);

    $scope.conversations.$loaded()
      .then(function(x) {
        $scope.sendMessage("HEJ");
      })
      .catch(function(error) {
        console.log("Error:", error);
      });

    //$scope.hashMap = new HashMap();

    /*var conversationsObj = $firebaseObject(Fire.conversations);
    conversationsObj.$bindTo($scope, "conversations").then(function() {
        console.log($scope.conversations.$value);

        if ($scope.conversations.$value == null)
            $scope.conversations.$value = [];

        $scope.sendMessage("HHEHEHEH");
    });*/

    /*var updateConversations = function() {
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
        if (value.content !== '<EMPTY>') {
            $scope.hashMap.get(value.talkId).messages.push(value);
        }
    }*/

    var createConversation = function(author, category, lat, long) {
        return {author: author, date: new Date().toLocaleString(), latitude: lat, longtitude: long, category: category, messages: []}
    }

    var createMessage = function(author, content) {
        return {content: content, author: author, date: new Date().toLocaleString()}
    }

    var addMessageToConversation = function(conversation, message) {
        if (conversation.messages == null)
            conversation.messages = [];

        conversation.messages.push(message);
    }

    var createAuthorModel = function(uid, name, avatarUrl) {
        return {uid: uid, name: name, avatarUrl: avatarUrl};
    }

    $scope.sendMessage = function(content) {
        var user = $cookies.getObject('user');
        if (user != null) {
            var author = createAuthorModel(user.uid, user.nick, user.img);

            var conversation = createConversation(author, "kategoria1", "50.0", "49.0");

            var message = createMessage(author, content);

            $scope.conversations.$add(conversation)
                .then(function(ref) {
                     var id = ref.key();

                     var idx = $scope.conversations.$indexFor(id);

                     console.log($scope.conversations[idx]);

                     addMessageToConversation($scope.conversations[idx], message);

                     $scope.conversations.$save($scope.conversations[idx]);
               });

        }
    }





//    //Odbieranie wszystkich
//    var getAll = function() {
//        var ref = Fire.all;
//
//        ref.once("value", function(data) {
//            var tmp = data.val();
//
//            angular.forEach(tmp, function(value, key) {
//                addToHashMap(value);
//            });
//
//            console.log($scope.conversations);
//            angular.forEach($scope.conversations, function(value, key) {
//                console.log("key: " + key)
//                $scope.markers.push({
//                    // layer: 'messages',
//                    lat: value.latitude,
//                    lng: value.longtitude,
//                    getMessageScope: function() {
//                        return $scope;
//                    },
//                    message: '<conversation-popup conversation="conversations[' + key + ']"></conversation-popup>'
//                });
//            })
//            updateConversations();
//        });
//    }
//
//  //  getAll();


    //Odbieranie
    /*var startListening = function() {
        var webMessages = Fire.messages;
        webMessages.on("child_added", function(snapshot, prevChildKey) {

            var message = snapshot.val();
            var notOnTheMap = false;
            console.log("get message" + message.content);
            if (!$scope.hashMap.has(message.talkId)) {
                notOnTheMap = true;
            }
            addToHashMap(message);
            updateConversations();
            if (notOnTheMap == true) {
                var arrayLength = $scope.conversations.length;
                var key = null;
                for (var i = 0; i < arrayLength; i++) {
                    if ($scope.conversations[i].talkId == message.talkId) key = i;
                }
                $scope.markers.push({
                    // layer: 'messages',
                    lat: message.latitude,
                    lng: message.longtitude,
                    getMessageScope: function() {
                        return $scope;
                    },
                    message: '<conversation-popup conversation="conversations[' + key + ']"></conversation-popup>'
                });
            }

            leafletData.getMap().then(function(map) {
                console.log(map);
                map._onResize();
            })
            $timeout(function() {
                if ($(".messages-wrapper").length > 0) {
                    $(".messages-wrapper").scrollTop($(".messages-wrapper")[0].scrollHeight);
                }
            }, 0);
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
        var webMessages = Fire.messages;
        var user = $cookies.getObject('user');
        if (user != null) {
            var webMessages = Fire.messages;
            webMessages.push({
                talkId: guid(),
                author: user.nick,
                content: '<EMPTY>',
                date: new Date().toLocaleString(),
                latitude: leafEvent.latlng.lat,
                longtitude: leafEvent.latlng.lng,
                img: user.img
            });
            toastr.success("Konwersacja utworzona");
        } else {
            toastr.error("Nie można wysłać wiadomości")
        }
    });*/

}]);
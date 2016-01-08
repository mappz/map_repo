var messagesModule = angular.module("messagesModule");
messagesModule.controller('messagesController', ['$scope', '$http', 'currentAuth', '$cookies', 'toastr', '$timeout', 'leafletData', 'fire', 'leafletMarkersHelpers', '$firebaseArray', 'conversationFactory', function($scope, $http, currentAuth, $cookies, toastr, $timeout, leafletData, Fire, leafletMarkersHelpers, $firebaseArray, conversationFactory) {
    $scope.openedConversation = false;
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

    $scope.conversations.$watch(function(evt) {
        console.log("Watch event");
        console.log(evt);
        var conversationId = $scope.conversations.$indexFor(evt.key);
        console.log("convId:" + conversationId);
        if (evt.event === 'child_added') {
            $scope.conversations[conversationId]
            $scope.markers.push({
                 layer: 'messages',
                msgKey: evt.key,
                lat: $scope.conversations[conversationId].latitude,
                lng: $scope.conversations[conversationId].longitude,
                getMessageScope: function() {
                    return $scope;
                },
                message: '<conversation-popup key="' + evt.key + '" conversations="conversations" markers="markers"></conversation-popup>'
            });
            $timeout(function() {
                if ($(".messages-wrapper").length > 0) {
                    $(".messages-wrapper").scrollTop($(".messages-wrapper")[0].scrollHeight);
                }
            }, 0);
        } else if (evt.event === 'child_removed') {
            var index = 0;
            angular.forEach($scope.markers, function(value, key) {
                if (evt.key == value.msgKey) $scope.markers.splice(index, 1);
                index++;
            })

        } else if (evt.event === 'child_changed') {

        }
    })
    $scope.conversations.$loaded()
        .then(function(x) {
            toastr.success("Conversations loaded")
        })
        .catch(function(error) {
            console.log("Error:", error);
        });

    $scope.$on("leafletDirectiveMap.popupopen", function(event, args) {
        $scope.openedConversation = true;
    })
    $scope.$on("leafletDirectiveMap.popupclose", function(event, args) {
        $timeout(function() {
            $scope.openedConversation = false;
        }, 100);
    })
    $scope.$on("leafletDirectiveMap.click", function(event, args) {
        if ($scope.openedConversation == false) {
            var leafEvent = args.leafletEvent;
            var user = $cookies.getObject('user');
            var author = conversationFactory.createAuthorModel(user.uid, user.nick, user.img);
            var conversation = conversationFactory.createConversation(author, "sport", leafEvent.latlng.lat, leafEvent.latlng.lng);
            $scope.conversations.$add(conversation)
                .then(function(ref) {
                    var id = ref.key();
                    var idx = $scope.conversations.$indexFor(id);
                    console.log($scope.conversations[idx]);
                });
        }
    })

}]);
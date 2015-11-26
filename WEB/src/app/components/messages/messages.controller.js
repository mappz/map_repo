var messagesModule = angular.module("messagesModule");
messagesModule.controller('messagesController', ['$scope', '$http','currentAuth', function($scope, $http,currentAuth) {
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
            $scope.hashMap.set(value.talkId, {talkId: value.talkId, latitude: value.latitude, longtitude: value.longtitude, messages: []});
        }

        $scope.hashMap.get(value.talkId).messages.push(value);
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
                angular.forEach($scope.conversations,function(value,key){
        console.log("key: "+ key)
            $scope.markers.push({
                lat: value.latitude,
                lng: value.longtitude,
                getMessageScope: function () { return $scope; },
                message: '<conversation-popup conversation="conversations['+key+']"></conversation-popup>'
            });
        })
            updateConversations();
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

    //$scope.sendMessage();


    //Odbieranie
    var startListening = function() {
        var ref = new Firebase("https://dazzling-fire-990.firebaseio.com");
        var webMessages = ref.child("webMessages");

        webMessages.on("child_added", function(snapshot, prevChildKey) {
          var message = snapshot.val();

          addToHashMap(message);

          updateConversations();
        });
    }

    startListening();

        $scope.$on("leafletDirectiveMap.click", function(event, args) {
            var leafEvent = args.leafletEvent;

    console.log(leafEvent.latlng.lat+" -> "+leafEvent.latlng.lng )
            $scope.markers.push({
                lat: leafEvent.latlng.lat,
                lng: leafEvent.latlng.lng,
                getMessageScope: function () { return $scope; },
                message: '<conversation-popup conversation="conversations"></conversation-popup>'
            });
        });

}]);
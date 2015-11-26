var fireService = angular.module("fireService", []);
fireService.factory("fire", function() {
    var applicationURL = "https://dazzling-fire-990.firebaseio.com";
    var messageModel = "messages";
    var userExtraDataModel = "userEx";

    var ref_x = new Firebase(applicationURL);
    var messages_x = ref_x.child(messageModel);
    var all_x = new Firebase(applicationURL+"/"+messageModel);

    return {
        ref: ref_x,
        messages: messages_x,
	all: all_x
    }
});

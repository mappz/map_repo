var fireService = angular.module("fireService", []);
fireService.factory("fire", function() {
    var applicationURL = "https://dazzling-fire-990.firebaseio.com";
    var messageModel = "messages";
    var conversationModel = "conversations";
    var userExtraDataModel = "userEx";

    var ref_x = new Firebase(applicationURL);
    var messages_x = ref_x.child(messageModel);
    var all_x = new Firebase(applicationURL + "/" + messageModel);
    var conversations_x = ref_x.child(conversationModel);

    return {
        ref: ref_x,
        messages: messages_x,
        conversations: conversations_x,
        all: all_x
    }
});
var fireService = angular.module("fireService", []);
/**
 * Factory that keeps data for api comunication
 * @return ref firebase referance,
 * @return messages firebase message referance,
 * @return userEx userEx firebase reference,
 * @return conversations conversations firebase reference,
 * @return all reference to all messages
 */
var fireFactory = function() {
/**
 * Aplication URL
 */
    var applicationURL = "https://dazzling-fire-990.firebaseio.com";
/**
 * message model name
 */
    var messageModel = "messages";
/**
 * convarsation model name
 */
    var conversationModel = "conversations";
/**
 * user extended data model name
 */
    var userExtraDataModel = "userEx";

    var ref_x = new Firebase(applicationURL);
    var messages_x = ref_x.child(messageModel);
    var userEx_x = ref_x.child(userExtraDataModel);
    var all_x = new Firebase(applicationURL + "/" + messageModel);
    var conversations_x = ref_x.child(conversationModel);

    return {
        ref: ref_x,
        messages: messages_x,
        userEx: userEx_x,
        conversations: conversations_x,
        all: all_x
    }
};
fireService.factory("fire", fireFactory);

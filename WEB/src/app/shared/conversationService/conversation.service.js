var conversationService = angular.module("conversationService", [])
/**
 * Conversation factory
 * @returns {undefined} nothing
 */
 var conversationFactory = function() {
/**
 * Crate conversation
 * @param author of conversation
 * @param category conversation category
 * @param lat latitude
 * @param long longtitude
 * @returns {convarsation} convarsation object
 */
    var createConversation = function(author, category, lat, long) {
        return {
            author: author,
            date: new Date().toLocaleString(),
            latitude: lat,
            longitude: long,
            category: category,
            messages: []
        }
    }
/**
 * Crate message object
 * @param author of conversation
 * @param content content of the messege
 * @returns {message} message object
 */
    var createMessage = function(author, content) {
        return {
            content: content,
            author: author,
            date: new Date().toLocaleString()
        }
    }
/**
 * Adds message to conversation
 * @param conversation conversation object
 * @param message new message
 * @returns {undefined} nothing
 */
    var addMessageToConversation = function(conversation, message) {
        if (conversation.messages == null)
            conversation.messages = [];

        conversation.messages.push(message);
    }
/**
 * Creates author object
 * @param uid unique user id
 * @param name user name
 * @param avatarUrl url to avatar image
 * @returns {undefined} nothing
 */
    var createAuthorModel = function(uid, name, avatarUrl) {
        return {
            uid: uid,
            name: name,
            avatarUrl: avatarUrl
        };
    }
    return {
        createConversation: createConversation,
        createMessage: createMessage,
        addMessageToConversation: addMessageToConversation,
        createAuthorModel: createAuthorModel
    }
}
conversationService.factory("conversationFactory",conversationFactory);

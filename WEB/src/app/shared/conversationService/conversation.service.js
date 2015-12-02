var conversationService = angular.module("conversationService", [])

conversationService.factory("conversationFactory", function() {
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

    var createMessage = function(author, content) {
        return {
            content: content,
            author: author,
            date: new Date().toLocaleString()
        }
    }

    var addMessageToConversation = function(conversation, message) {
        if (conversation.messages == null)
            conversation.messages = [];

        conversation.messages.push(message);
    }

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
})
describe('conversationFactory test', function() {
    describe('when createConversation', function() {
        it("returns conversation", function() {
            var $injector = angular.injector(['conversationService']);
            var conversationFactory = $injector.get('conversationFactory');
            var conversation = conversationFactory.createConversation("Test", "TestCat", 1, 0);
            expect(conversation.author).toEqual("Test");
            expect(conversation.category).toEqual("TestCat");
            expect(conversation.latitude).toEqual(1);
            expect(conversation.longitude).toEqual(0);
        });
    });
    describe('when createMessage', function() {
        it("returns new message", function() {
            var $injector = angular.injector(['conversationService']);
            var conversationFactory = $injector.get('conversationFactory');
            var message = conversationFactory.createMessage("Author","Message");
            expect(message.author).toEqual("Author");
            expect(message.content).toEqual("Message");
            expect(message.date).toBeDefined();
        });
    });
    describe('when addMessageToConversation', function() {
        it("returns conversation with new message", function() {
            var $injector = angular.injector(['conversationService']);
            var conversationFactory = $injector.get('conversationFactory');
            var message = conversationFactory.createMessage("Author","Message");
            var message2 = conversationFactory.createMessage("Author2","Message2");
            var conversation = conversationFactory.createConversation("Author","C",0,1);
            expect(conversation).toBeDefined();
            conversationFactory.addMessageToConversation(conversation,message);
            conversationFactory.addMessageToConversation(conversation,message2);
            expect(conversation.messages[0]).toEqual(message);
            expect(conversation.messages[1]).toEqual(message2);
        });
    });
    describe('when createAuthorModel', function() {
        it("returns authorModel", function() {
            var $injector = angular.injector(['conversationService']);
            var conversationFactory = $injector.get('conversationFactory');
            var model = {
            uid: "123",
            name: "John Doe",
            avatarUrl: "http:\\\\testurl.pl\\avatar.jpg"
            }
            var authorModel = conversationFactory.createAuthorModel("123","John Doe","http:\\\\testurl.pl\\avatar.jpg");
            var message = conversationFactory.createMessage(authorModel,"Message");
            expect(message).toBeDefined();
            expect(message.author).toEqual(model);
        });
    });
});
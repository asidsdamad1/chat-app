package org.asi.messageswebsocketservice.web.websocket;

import org.asi.dtomodels.ChatMessageDTO;
import org.asi.messageswebsocketservice.messaging.sender.StoringMessagesSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessagesControllerTest {

    @Mock
    private SimpMessagingTemplate simpMessagingTemplate;

    @Mock
    private StoringMessagesSender storingMessagesSender;

    @InjectMocks
    private MessagesController messagesController;

    private ChatMessageDTO chatMessage;
    private static final String TEST_SENDER = "testSender";
    private static final String TEST_RECIPIENT = "testRecipient";
    private static final String TEST_CONTENT = "Hello, this is a test message!";

    @BeforeEach
    void setUp() {
        chatMessage = new ChatMessageDTO();
        chatMessage.setSender(TEST_SENDER);
        chatMessage.setRecipient(TEST_RECIPIENT);
        chatMessage.setContent(TEST_CONTENT);
    }

    @Test
    void processMessage_ShouldSendMessageToTopicAndStore() {
        // Arrange
        String expectedDestination = "/topic/" + TEST_RECIPIENT + ".messages";
        doNothing().when(simpMessagingTemplate).convertAndSend(anyString(), any(ChatMessageDTO.class));
        doNothing().when(storingMessagesSender).send(any(ChatMessageDTO.class));

        // Act
        messagesController.processMessage(chatMessage);

        // Assert
        verify(simpMessagingTemplate).convertAndSend(expectedDestination, chatMessage);
        verify(storingMessagesSender).send(chatMessage);
        verifyNoMoreInteractions(simpMessagingTemplate, storingMessagesSender);
    }

    @Test
    void processMessage_WithNullRecipient_ShouldStillProcess() {
        // Arrange
        chatMessage.setRecipient(null);
        String expectedDestination = "/topic/null.messages";

        // Act
        messagesController.processMessage(chatMessage);

        // Assert
        verify(simpMessagingTemplate).convertAndSend(expectedDestination, chatMessage);
        verify(storingMessagesSender).send(chatMessage);
    }

    @Test
    void processMessage_ShouldHandleEmptyContent() {
        // Arrange
        chatMessage.setContent("");
        String expectedDestination = "/topic/" + TEST_RECIPIENT + ".messages";

        // Act
        messagesController.processMessage(chatMessage);

        // Assert
        verify(simpMessagingTemplate).convertAndSend(expectedDestination, chatMessage);
        verify(storingMessagesSender).send(chatMessage);
    }

    @Test
    void processMessage_VerifyMessageOrder() {
        // Arrange
        String expectedDestination = "/topic/" + TEST_RECIPIENT + ".messages";

        // Act
        messagesController.processMessage(chatMessage);

        // Assert
        // Verify that message is first sent to the topic and then stored
        inOrder(simpMessagingTemplate, storingMessagesSender).verify(simpMessagingTemplate)
                .convertAndSend(expectedDestination, chatMessage);
        inOrder(simpMessagingTemplate, storingMessagesSender).verify(storingMessagesSender)
                .send(chatMessage);
    }
} 
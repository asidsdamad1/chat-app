package org.asi.messageswebsocketservice.web.websocket;

import org.asi.dtomodels.ChatMessageDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import org.asi.messageswebsocketservice.messaging.sender.StoringMessagesSender;


@RestController
public class MessagesController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final StoringMessagesSender storingMessagesSender;

    public MessagesController(SimpMessagingTemplate simpMessagingTemplate,
                              StoringMessagesSender storingMessagesSender) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.storingMessagesSender = storingMessagesSender;
    }

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessageDTO chatMessage) {
        simpMessagingTemplate.convertAndSend("/topic/" + chatMessage.getRecipient() + ".messages", chatMessage);
        storingMessagesSender.send(chatMessage);
    }

}

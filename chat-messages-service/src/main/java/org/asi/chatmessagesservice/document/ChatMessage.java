package org.asi.chatmessagesservice.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.asi.chatmessagesservice.constant.MessageStatus;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chat_message")
public class ChatMessage {

    @Id
    private String id;

    @Field(name = "friend_chat")
    private Long friendChat;
    private String sender;
    private String recipient;
    private String content;
    private Date time;
    private MessageStatus status;

}

package org.asi.chatmessagesservice.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.asi.chatmessagesservice.document.ChatMessage;
import org.asi.chatmessagesservice.security.SecurityUtils;
import org.asi.chatmessagesservice.service.ChatMessageService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/chat-messages")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    public ChatMessageController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @Operation(summary = "Get last users messages",
            description = "Get last messages in users friend chat.")
    @GetMapping
    public Flux<ChatMessage> getLastUsersMessagesFromTimeWithSize(@RequestParam("friend_chat_id1") long friendChatId1,
                                                                  @RequestParam("friend_chat_id2") long friendChatId2,
                                                                  @RequestParam(value = "from", required = false) String fromTime,
                                                                  @RequestParam("size") int numberOfMessagesToFetch) {
        if (fromTime != null) {
            return chatMessageService.findLastUsersMessagesFromTime(friendChatId1, friendChatId2, fromTime, numberOfMessagesToFetch);
        } else {
            return chatMessageService.getLastUserMessages(friendChatId1, friendChatId2, numberOfMessagesToFetch);
        }
    }

    @Operation(summary = "Set delivered status for messages by friend chat id.",
            description = "For authenticated user set delivered status for all chat messages by friend chat id.")
    @PatchMapping(params = "friend_chat_id")
    public Mono<Void> setDeliveredStatusForAllRecipientMessagesInFriendChat(@RequestParam("friend_chat_id") long friendChatId) {
        return SecurityUtils.getCurrentUser()
                .flatMap(currentUser -> chatMessageService.setDeliveredStatusForAllRecipientMessagesInFriendChat(friendChatId, currentUser));
    }

}

package org.asi.chatservice.web.rest;

import lombok.RequiredArgsConstructor;
import org.asi.authutils.SecurityUtils;
import org.asi.chatservice.dto.FriendChatDTO;
import org.asi.chatservice.dto.mapper.FriendChatMapper;
import org.asi.chatservice.messaging.sender.DeleteMessagesSender;
import org.asi.chatservice.service.FriendChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friends-chats")
public class FriendChatController {

    private final FriendChatService friendChatService;
    private final FriendChatMapper friendChatMapper;
    private final DeleteMessagesSender deleteMessagesSender;

    @GetMapping
    public ResponseEntity<List<FriendChatDTO>> getAllFriendsChats() {

        var allFriendsChatsBySender = friendChatService.getAllFriendsChatsBySender(SecurityUtils.getCurrentUser());

        return ResponseEntity.ok()
                .body(friendChatMapper.mapToFriendChatList(allFriendsChatsBySender));
    }

    @DeleteMapping(params = {"friend_chat", "friend_chat_with"})
    public ResponseEntity<Void> deleteUserFriendChat(@RequestParam("friend_chat") long friendChatId,
                                                     @RequestParam("friend_chat_with") long friendChatWithId) {
        friendChatService.deleteFriendChat(friendChatId, friendChatWithId, SecurityUtils.getCurrentUser());
        deleteMessagesSender.sendDeletingMessagesTask(List.of(friendChatId, friendChatWithId));
        return ResponseEntity.noContent().build();
    }


}

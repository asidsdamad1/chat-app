package org.asi.chatservice.service;


import org.asi.chatservice.domain.ChatProfile;
import org.asi.chatservice.domain.FriendChat;

import java.util.List;

public interface FriendChatService {
    void createFriendChat(ChatProfile firstUserChatProfile, ChatProfile secondUserChatProfile);

    List<FriendChat> getAllFriendsChatsBySender(String currentUser);

    void deleteFriendChat(long friendChatId, long friendChatWithId, String currentUserId);
}

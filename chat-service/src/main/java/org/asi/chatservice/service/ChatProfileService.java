package org.asi.chatservice.service;


import org.asi.chatservice.domain.ChatProfile;

public interface ChatProfileService {
    ChatProfile createChatProfile(String userId, String username);

    ChatProfile generateNewFriendsRequestCode(String userId, String username);

    ChatProfile getChatProfileById(String userId);
}

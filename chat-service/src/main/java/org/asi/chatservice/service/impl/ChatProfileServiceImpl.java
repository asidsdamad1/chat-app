package org.asi.chatservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.asi.chatservice.domain.ChatProfile;
import org.asi.chatservice.repository.ChatProfileRepository;
import org.asi.chatservice.service.ChatProfileService;
import org.asi.exceptionutils.InvalidDataException;
import org.asi.exceptionutils.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatProfileServiceImpl implements ChatProfileService {

    private final ChatProfileRepository chatProfileRepository;

    @Override
    public ChatProfile createChatProfile(String userId, String username) {

        if (!StringUtils.isNotBlank(userId)) {
            throw new InvalidDataException("Chat profile can't be created because user id is empty.");
        }
        if (!StringUtils.isNotBlank(username)) {
            throw new InvalidDataException("Chat profile can't be created because username is empty.");
        }

        var chatProfile = new ChatProfile();
        chatProfile.setUserId(UUID.fromString(userId));
        chatProfile.setFriendsRequestCode(generateFriendRequestCode(username));
        return chatProfileRepository.save(chatProfile);
    }

    @Override
    public ChatProfile generateNewFriendsRequestCode(String userId, String username) {
        var chatProfile = getChatProfileById(userId);
        chatProfile.setFriendsRequestCode(generateFriendRequestCode(username));
        return chatProfileRepository.save(chatProfile);
    }

    private String generateFriendRequestCode(String username) {
        return username.toLowerCase() + "-" + RandomStringUtils.randomNumeric(10);
    }

    @Override
    public ChatProfile getChatProfileById(String userId) {
        return chatProfileRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new NotFoundException("Not found chat profile for user " + userId));
    }


}

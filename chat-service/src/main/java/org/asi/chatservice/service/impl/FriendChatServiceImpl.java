package org.asi.chatservice.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.asi.chatservice.domain.ChatProfile;
import org.asi.chatservice.domain.FriendChat;
import org.asi.chatservice.repository.ChatProfileRepository;
import org.asi.chatservice.repository.FriendChatRepository;
import org.asi.chatservice.repository.FriendRequestRepository;
import org.asi.chatservice.service.FriendChatService;
import org.asi.exceptionutils.AlreadyExistsException;
import org.asi.exceptionutils.NotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class FriendChatServiceImpl implements FriendChatService {

    private final FriendChatRepository friendChatRepository;
    private final ChatProfileRepository chatProfileRepository;
    private final FriendRequestRepository friendRequestRepository;

    public FriendChatServiceImpl(FriendChatRepository friendChatRepository,
                                 ChatProfileRepository chatProfileRepository,
                                 FriendRequestRepository friendRequestRepository) {
        this.friendChatRepository = friendChatRepository;
        this.chatProfileRepository = chatProfileRepository;
        this.friendRequestRepository = friendRequestRepository;
    }

    @Transactional
    @Override
    public void createFriendChat(ChatProfile firstUserChatProfile, ChatProfile secondUserChatProfile) {

        if (friendChatRepository.existsFriendChatForUsers(firstUserChatProfile, secondUserChatProfile)) {
            throw new AlreadyExistsException("Chat for users already exists");
        }

        var friendChatForFirstUser = new FriendChat();
        var friendChatForSecondUser = new FriendChat();

        friendChatForFirstUser.setSender(firstUserChatProfile);
        friendChatForFirstUser.setRecipient(secondUserChatProfile);

        friendChatForSecondUser.setSender(secondUserChatProfile);
        friendChatForSecondUser.setRecipient(firstUserChatProfile);
        friendChatRepository.save(friendChatForFirstUser);
        friendChatRepository.save(friendChatForSecondUser);

        friendChatForFirstUser.setChatWith(friendChatForSecondUser);
        friendChatForSecondUser.setChatWith(friendChatForFirstUser);

        friendChatRepository.save(friendChatForFirstUser);
        friendChatRepository.save(friendChatForSecondUser);

    }

    @Override
    public List<FriendChat> getAllFriendsChatsBySender(String currentUserId) {
        return chatProfileRepository.findById(UUID.fromString(currentUserId))
                .map(friendChatRepository::findBySender)
                .orElseThrow(() -> new NotFoundException("User with id " + currentUserId + " not found"));
    }

    @Transactional
    @Override
    public void deleteFriendChat(long friendChatId, long friendChatWithId, String currentUserId) {
        var friendChat = friendChatRepository.findByIdAndFriendChatWithIdAndSenderId(friendChatId, friendChatWithId,
                UUID.fromString(currentUserId))
                .orElseThrow(() -> new NotFoundException("Friend chat not found"));
        friendRequestRepository.deleteFriendRequestByChatProfiles(friendChat.getSender(), friendChat.getRecipient());
        friendChatRepository.delete(friendChat);
    }


}

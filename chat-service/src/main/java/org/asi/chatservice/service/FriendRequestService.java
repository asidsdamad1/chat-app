package org.asi.chatservice.service;


import org.asi.chatservice.domain.FriendRequest;

import java.util.List;

public interface FriendRequestService {
    FriendRequest createNewFriendRequest(String currentUserId, String friendRequestCode);

    void replyToFriendRequest(long friendRequestId, String currentUserId, boolean accept);

    void deleteFriendRequestBySender(String senderId, long friendRequestId);

    List<FriendRequest> getAllNotAcceptedFriendRequestsByRecipientId(String recipientId);

    List<FriendRequest> getAllNotAcceptedFriendRequestsBySenderId(String senderId);

}

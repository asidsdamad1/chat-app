package org.asi.chatservice.repository;

import org.asi.chatservice.domain.ChatProfile;
import org.asi.chatservice.domain.FriendChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FriendChatRepository extends JpaRepository<FriendChat, Long> {


    @Query("SELECT " +
            "CASE WHEN COUNT (fc) > 0 THEN true ELSE false END " +
            "FROM FriendChat fc WHERE (fc.sender = :chatProfile1 AND fc.recipient = :chatProfile2) " +
            "OR (fc.sender = :chatProfile2 AND fc.recipient = :chatProfile1)")
    boolean existsFriendChatForUsers(ChatProfile chatProfile1, ChatProfile chatProfile2);


    List<FriendChat> findBySender(ChatProfile sender);


    @Query("SELECT fc from FriendChat fc WHERE fc.id = :friendChatId " +
            "AND fc.chatWith.id = :friendChatWithId " +
            "AND fc.sender.userId = :senderId")
    Optional<FriendChat> findByIdAndFriendChatWithIdAndSenderId(long friendChatId,
                                                                long friendChatWithId,
                                                                UUID senderId);


}

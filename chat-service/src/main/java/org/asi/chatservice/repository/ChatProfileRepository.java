package org.asi.chatservice.repository;


import org.asi.chatservice.domain.ChatProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatProfileRepository extends JpaRepository<ChatProfile, UUID> {

    Optional<ChatProfile> findByFriendsRequestCode(String friendsRequestCode);

}

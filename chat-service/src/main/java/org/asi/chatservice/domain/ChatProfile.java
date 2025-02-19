package org.asi.chatservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@Table(name = "chat_profile", schema = "chat_service_database")
@Entity
@NoArgsConstructor
public class ChatProfile {

    @Id
    @Column(nullable = false, name = "user_id", unique = true, columnDefinition = "BINARY(16)")
    private UUID userId;

    @Column(name = "friends_request_code", nullable = false, length = 64, unique = true)
    private String friendsRequestCode;

}

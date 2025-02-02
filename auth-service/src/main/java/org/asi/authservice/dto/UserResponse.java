package org.asi.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.asi.authservice.model.User;

@Data
@Builder
@AllArgsConstructor
public class UserResponse {
    private String userName;
    private String email;
    private String firstName;
    private String lastName;

    public static UserResponse of(User user) {
        return UserResponse.builder()
                .userName(user.username())
                .email(user.email())
                .firstName(user.firstName())
                .lastName(user.lastName())
                .build();
    }
}

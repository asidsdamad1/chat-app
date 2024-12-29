package org.asi.authservice.dto;

import lombok.Data;

@Data
public class UserResponse {
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
}

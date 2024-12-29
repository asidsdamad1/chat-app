package org.asi.authservice.web.controller.payload;

import lombok.Data;

@Data
public class UserRequest {
    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
}

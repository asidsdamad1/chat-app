package org.asi.authservice.web.controller.payload;

import lombok.Getter;

@Getter
public class AuthRequest {
    private String username;
    private String password;
}

package org.asi.dtomodels;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
public class UserRequest {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
}

package org.asi.dtomodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {
    private String id;

    @NotBlank
    @Size(min = 4, max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    private String username;

    @JsonProperty(value = "password", access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank
    @Size(min = 6, max = 32)
    private String password;

    @Email
    @NotBlank
    private String email;

    @Pattern(regexp = "^[a-zA-Z]*$")
    @Size(min = 2, max = 50)
    @NotBlank
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z]*$")
    @Size(min = 2, max = 50)
    @NotBlank
    private String lastName;
    private String activationKey;

    public UserDTO(String username, String password, String firstName, String lastName, String email) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}

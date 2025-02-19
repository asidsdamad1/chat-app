package org.asi.authservice.web.controller.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.asi.authservice.constants.ApplicationConstants;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChangePassRequest {

    @NotBlank
    @Size(min = ApplicationConstants.USER_PASSWORD_MIN_LENGTH, max = ApplicationConstants.USER_PASSWORD_MAX_LENGTH)
    private String currentPassword;

    @NotBlank
    @Size(min = ApplicationConstants.USER_PASSWORD_MIN_LENGTH, max = ApplicationConstants.USER_PASSWORD_MAX_LENGTH)
    private String newPassword;

}

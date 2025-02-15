package org.asi.authservice.validate;

import org.apache.commons.lang3.StringUtils;
import org.asi.authservice.constants.ApplicationConstants;
import org.asi.dtomodels.UserDTO;
import org.asi.exceptionutils.InvalidDataException;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;

public class UserValidate {
    public void validateCreate(UserDTO request) {
        String userName = request.getUsername();
        String password = request.getPassword();
        String email = request.getEmail();
        String firstName = request.getFirstName();
        String lastName = request.getLastName();

        if (StringUtils.isEmpty(userName) || StringUtils.isBlank(userName) || !StringUtils.isAlphanumeric(userName)) {
            throw new InvalidDataException("Invalid username");
        }
        if (StringUtils.isEmpty(password) || StringUtils.isBlank(password)) {
            throw new InvalidDataException("Invalid password");
        }
        if (password.length() < ApplicationConstants.USER_PASSWORD_MIN_LENGTH
                || password.length() > ApplicationConstants.USER_PASSWORD_MAX_LENGTH) {
           throw new InvalidDataException("Invalid password length");
        }
        if (!new EmailValidator().isValid(email, null)) {
           throw new InvalidDataException("Invalid email");
        }
        if (StringUtils.isEmpty(firstName) || StringUtils.isBlank(firstName)) {
           throw new InvalidDataException("Invalid first name");
        }
        if (StringUtils.isEmpty(lastName) || StringUtils.isBlank(lastName)) {
           throw new InvalidDataException("Invalid last name");
        }
    }
}

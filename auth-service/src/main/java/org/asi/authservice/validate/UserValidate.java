package org.asi.authservice.validate;

import org.apache.commons.lang3.StringUtils;
import org.asi.authservice.constants.ApplicationConstants;
import org.asi.dtomodels.UserRequest;
import org.asi.exceptionutils.InvalidDataException;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;

public class UserValidate {
    public void validateCreate(UserRequest request) {
        String userName = request.username();
        String password = request.password();
        String email = request.email();
        String firstName = request.firstName();
        String lastName = request.lastName();

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

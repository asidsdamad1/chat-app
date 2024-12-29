package org.asi.authservice.validate;

import org.apache.commons.lang3.StringUtils;
import org.asi.authservice.constants.ApplicationConstants;
import org.asi.authservice.web.controller.payload.UserRequest;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;

public class UserValidate extends AbstractValidation{
    public void validateCreate(UserRequest request) {
        String userName = request.getUserName();
        String password = request.getPassword();
        String email = request.getPassword();
        String firstName = request.getPassword();
        String lastName = request.getPassword();

        if (StringUtils.isEmpty(userName) || StringUtils.isBlank(userName) || !StringUtils.isAlphanumeric(userName)) {
            getMessageDes().add("Invalid username");
        }
        if (StringUtils.isEmpty(password) || StringUtils.isBlank(password)) {
            getMessageDes().add("Invalid password");
        }
        if (password.length() < ApplicationConstants.USER_PASSWORD_MIN_LENGTH
                || password.length() > ApplicationConstants.USER_PASSWORD_MAX_LENGTH) {
            getMessageDes().add("Invalid password length");
        }
        if (!new EmailValidator().isValid(email, null)) {
            getMessageDes().add("Invalid email");
        }
        if (StringUtils.isEmpty(firstName) || StringUtils.isBlank(firstName)) {
            getMessageDes().add("Invalid first name");
        }
        if (StringUtils.isEmpty(lastName) || StringUtils.isBlank(lastName)) {
            getMessageDes().add("Invalid last name");
        }
    }
}

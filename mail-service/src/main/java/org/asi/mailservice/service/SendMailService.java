package org.asi.mailservice.service;

import org.asi.dtomodels.UserDTO;

public interface SendMailService {

    void sendActivationEmail(UserDTO user);
}

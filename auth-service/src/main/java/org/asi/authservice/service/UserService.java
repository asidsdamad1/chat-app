package org.asi.authservice.service;

import org.asi.authservice.model.User;

public interface UserService {
    void createUser(String username, String password, String email, String firstName, String lastName);

    void activateUser(String activationKey);

    User findUserById(String userId);

    User modifyUser(String userId, String firstName, String lastName);

    void changeUserPassword(String userId, String currentPassword, String newPassword);
}

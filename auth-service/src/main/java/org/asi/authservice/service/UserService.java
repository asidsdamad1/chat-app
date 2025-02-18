package org.asi.authservice.service;

import org.asi.authservice.model.User;
import org.asi.dtomodels.UserDTO;

public interface UserService {
    User createUser(UserDTO userDTO);

    void activateUser(String activationKey);

    User findUserById(String userId);

    User modifyUser(String userId, String firstName, String lastName);

    void changeUserPassword(String userId, String currentPassword, String newPassword);
}

package org.asi.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.asi.authservice.model.User;
import org.asi.authservice.repository.UserRepository;
import org.asi.authservice.service.UserService;
import org.asi.exceptionutils.AlreadyExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createUser(String username, String password, String email, String firstName, String lastName) {
        if (userRepository.existsByUsernameIgnoreCase(username)) {
            throw new AlreadyExistsException("User with username %s already exists".formatted(username));
        }

        if (userRepository.existsByUsernameIgnoreCase(email)) {
            throw new AlreadyExistsException("User with username %s already exists".formatted(email));
        }

        var user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .enabled(false)
                .build();

        userRepository.save(user);
    }

    @Override
    public void activateUser(String activationKey) {

    }

    @Override
    public User findUserById(String userId) {
        return null;
    }

    @Override
    public User modifyUser(String userId, String firstName, String lastName) {
        return null;
    }

    @Override
    public void changeUserPassword(String userId, String currentPassword, String newPassword) {

    }
}

package org.asi.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.asi.authservice.model.User;
import org.asi.authservice.repository.UserRepository;
import org.asi.authservice.service.UserService;
import org.asi.authservice.validate.UserValidate;
import org.asi.dtomodels.UserRequest;
import org.asi.exceptionutils.AlreadyExistsException;
import org.asi.exceptionutils.InvalidDataException;
import org.asi.exceptionutils.NotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(UserRequest userRequest) {
        log.info("Creating user with username: {}", userRequest.username());
        if (userRepository.existsByUsernameIgnoreCase(userRequest.username())) {
            throw new AlreadyExistsException("User with username %s already exists".formatted(userRequest.username()));
        }

        if (userRepository.existsByEmailIgnoreCase(userRequest.email())) {
            throw new AlreadyExistsException("User with username %s already exists".formatted(userRequest.email()));
        }

        new UserValidate().validateCreate(userRequest);

        var user = User.builder()
                .username(userRequest.username())
                .password(passwordEncoder.encode(userRequest.password()))
                .email(userRequest.email())
                .firstName(userRequest.firstName())
                .lastName(userRequest.lastName())
                .enabled(false)
                .build();

        return userRepository.save(user);
    }

    @Override
    public void activateUser(String activationKey) {

    }

    @Override
    public User findUserById(String userId) {
        return userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new NotFoundException("Not found user with id: " + userId));
    }

    @Override
    public User modifyUser(String userId, String firstName, String lastName) {
        var user = findUserById(userId);
        if (!user.id().toString().equals(userId)) {
            throw new InvalidDataException("Incorrect user id");
        }

        if (isNotEmpty(firstName)) {
            user.firstName(firstName);
        }

        if (isNotEmpty(lastName)) {
            user.firstName(lastName);
        }
        return userRepository.save(user);
    }

    @Override
    public void changeUserPassword(String userId, String currentPassword, String newPassword) {
        var user = findUserById(userId);
        if (!user.id().toString().equals(userId)) {
            throw new InvalidDataException("Incorrect user id");
        }
        if (!passwordEncoder.matches(currentPassword, user.password())) {
            throw new InvalidDataException("Incorrect current password");
        }

        user.password(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}

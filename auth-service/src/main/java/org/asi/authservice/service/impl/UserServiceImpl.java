package org.asi.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.asi.authservice.constants.RoleConstants;
import org.asi.authservice.model.Role;
import org.asi.authservice.model.User;
import org.asi.authservice.repository.RoleRepository;
import org.asi.authservice.repository.UserRepository;
import org.asi.authservice.service.UserService;
import org.asi.authservice.validate.UserValidate;
import org.asi.dtomodels.UserDTO;
import org.asi.exceptionutils.AlreadyExistsException;
import org.asi.exceptionutils.InvalidDataException;
import org.asi.exceptionutils.NotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public User createUser(UserDTO userDTO) {
        log.info("Creating user with username: {}", userDTO.getUsername());
        if (userRepository.existsByUsernameIgnoreCase(userDTO.getUsername())) {
            throw new AlreadyExistsException("User with username %s already exists".formatted(userDTO.getUsername()));
        }

        if (userRepository.existsByEmailIgnoreCase(userDTO.getEmail())) {
            throw new AlreadyExistsException("User with username %s already exists".formatted(userDTO.getEmail()));
        }

        new UserValidate().validateCreate(userDTO);

        var user = User.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .email(userDTO.getEmail())
                .activationKey(RandomStringUtils.randomAlphanumeric(124))
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .enabled(false)
                .build();
        var authorities = new HashSet<Role>();
        roleRepository.findByNameIgnoreCase(RoleConstants.ROLE_USER.name())
                .ifPresent(authorities::add);
        user.setRoles(authorities);

        return userRepository.save(user);
    }

    @Override
    public void activateUser(String activationKey) {
        if (isBlank(activationKey))
            throw new InvalidDataException("Invalid activation key");

        userRepository.findOneByActivationKey(activationKey)
                .ifPresent(user -> {
                    log.debug("Activation user with id {} with key {}", user.getId(), activationKey);
                    user.setEnabled(true);
                    user.setActivationKey(null);
                    userRepository.save(user);
                });
    }

    @Override
    public User findUserById(String userId) {
        return userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new NotFoundException("Not found user with id: " + userId));
    }

    @Override
    public User modifyUser(String userId, String firstName, String lastName) {
        var user = findUserById(userId);
        if (!user.getId().toString().equals(userId)) {
            throw new InvalidDataException("Incorrect user id");
        }

        if (isNotEmpty(firstName)) {
            user.setFirstName(firstName);
        }

        if (isNotEmpty(lastName)) {
            user.setLastName(lastName);
        }
        return userRepository.save(user);
    }

    @Override
    public void changeUserPassword(String userId, String currentPassword, String newPassword) {
        var user = findUserById(userId);
        if (!user.getId().toString().equals(userId)) {
            throw new InvalidDataException("Incorrect user id");
        }
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new InvalidDataException("Incorrect current password");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}

package org.asi.authservice.web.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.asi.authservice.mapper.UserMapper;
import org.asi.authservice.message.sender.UserSender;
import org.asi.authservice.security.model.CustomUserDetails;
import org.asi.authservice.service.UserService;
import org.asi.authservice.web.controller.payload.ChangePassRequest;
import org.asi.dtomodels.UserDTO;
import org.asi.exceptionutils.InvalidDataException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final UserSender userSender;

    @Operation(summary = "Create new user", description = "User registration endpoint", tags = "Registration")
    @PostMapping
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO userDTO) {
        UserDTO dto = userMapper.toDTO(userService.createUser(userDTO));
        userSender.send(dto);
        return ResponseEntity.ok(dto);
    }

    @PatchMapping("/activate")
    public ResponseEntity<Void> activateAccount(@RequestParam("data") String activationKey) {
        userService.activateUser(activationKey);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") String userId) {
        return ResponseEntity.ok(userMapper.toDTO(userService.findUserById(userId)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> editUser(@PathVariable("id") String userId, @RequestBody UserDTO userDTO) {
        var user = userService.modifyUser(userId, userDTO.getFirstName(), userDTO.getLastName());
        return ResponseEntity.ok(userMapper.toDTO(user));
    }


    @PatchMapping("/{id}/change-password")
    public ResponseEntity<Void> changeUserPassword(@PathVariable("id") String userId,
                                                   @Valid @RequestBody ChangePassRequest request,
                                                   Authentication authentication) {
        var currentUser = (CustomUserDetails) authentication.getPrincipal();
        var currentUserId = currentUser.getUser().getId().toString();
        if (!userId.equals(currentUserId)) {
            throw new InvalidDataException("Invalid user id");
        }
        userService.changeUserPassword(currentUserId, request.getCurrentPassword(), request.getNewPassword());
        return ResponseEntity.noContent().build();
    }

}

package org.asi.authservice.web.controller.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.asi.authservice.mapper.UserMapper;
import org.asi.authservice.security.SecurityUserDetailsImpl;
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

    @PostMapping
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userMapper.toDTO(userService.createUser(userDTO)));
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
        var currentUser = (SecurityUserDetailsImpl) authentication.getPrincipal();
        if (!userId.equals(currentUser.getId())) {
            throw new InvalidDataException("Invalid user id");
        }
        userService.changeUserPassword(currentUser.getId(), request.getCurrentPassword(), request.getNewPassword());
        return ResponseEntity.noContent().build();
    }

}

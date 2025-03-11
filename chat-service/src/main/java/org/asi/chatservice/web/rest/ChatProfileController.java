package org.asi.chatservice.rest;

import lombok.RequiredArgsConstructor;
import org.asi.authutils.SecurityUtils;
import org.asi.chatservice.dto.ChatProfileDTO;
import org.asi.chatservice.dto.mapper.ChatProfileMapper;
import org.asi.chatservice.service.ChatProfileService;
import org.asi.exceptionutils.InvalidDataException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat-profiles")
public class ChatProfileController {

    private final ChatProfileService chatProfileService;
    private final ChatProfileMapper chatProfileMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ChatProfileDTO> getChatProfileById(@PathVariable("id") String id) {
        return ResponseEntity.ok()
                .body(chatProfileMapper.chatProfileToChatProfileDTO(chatProfileService.getChatProfileById(id)));
    }

    @PatchMapping("/{id}/new-friends-request-code")
    public ResponseEntity<ChatProfileDTO> generateNewFriendsRequestCode(@PathVariable("id") String userId) {

        if (!userId.equals(SecurityUtils.getCurrentUser())) {
            throw new InvalidDataException("Invalid user id");
        }
        var chatProfile = chatProfileService.generateNewFriendsRequestCode(userId, SecurityUtils.getCurrentUserPreferredUsername());
        return ResponseEntity.ok()
                .body(chatProfileMapper.chatProfileToChatProfileDTO(chatProfile));
    }


}

package org.asi.chatservice.messaging.listener;

import lombok.extern.slf4j.Slf4j;
import org.asi.chatservice.service.ChatProfileService;
import org.asi.dtomodels.UserDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitNewUserListener {

    private final ChatProfileService chatProfileService;

    public RabbitNewUserListener(ChatProfileService chatProfileService) {
        this.chatProfileService = chatProfileService;
    }


    @RabbitListener(queues = "#{newUsersQueue.name}")
    public void receiveNewUser(UserDTO userDTO) {
        log.debug("New user {}", userDTO.getUsername());
        chatProfileService.createChatProfile(userDTO.getId(), userDTO.getUsername());
    }


}

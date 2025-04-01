package org.asi.mailservice.listener;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.asi.dtomodels.UserDTO;
import org.asi.mailservice.service.SendMailService;

@Component
public class RabbitUserActivationListener {

    private final SendMailService sendMailService;

    public RabbitUserActivationListener(SendMailService sendMailService) {
        this.sendMailService = sendMailService;

    }

    @RabbitListener(queues = "#{usersActivationQueue.name}")
    public void receiveNewUser(UserDTO userDTO) {
        sendMailService.sendActivationEmail(userDTO);
    }


}

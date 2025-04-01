package org.asi.authservice.message.sender;

import lombok.RequiredArgsConstructor;
import org.asi.dtomodels.UserDTO;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;

@RequiredArgsConstructor
public class UserSender {
    private final RabbitTemplate rabbitTemplate;
    private final FanoutExchange fanout;

    public void send(UserDTO userDTO) {
        var converter = rabbitTemplate.getMessageConverter();
        var properties = new MessageProperties();
        var message = converter.toMessage(userDTO, properties);
        rabbitTemplate.send(fanout.getName(), "", message);
    }
}

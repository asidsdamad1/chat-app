package org.asi.authservice.config;

import org.asi.authservice.message.sender.UserSender;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("org.asi.authservice.fanout");
    }


    @Bean
    public UserSender userSender(RabbitTemplate rabbitTemplate, FanoutExchange fanoutExchange) {
        return new UserSender(rabbitTemplate, fanoutExchange);
    }


}

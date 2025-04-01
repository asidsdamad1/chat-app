package org.asi.chatservice.config;

import org.asi.chatservice.messaging.sender.DeleteMessagesSender;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
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
    public Queue newUsersQueue() {
        return new Queue("org.asi.chatservice.account");
    }

    @Bean
    public Binding bindingActivationMail(FanoutExchange fanoutExchange, Queue newUsersQueue) {
        return BindingBuilder.bind(newUsersQueue).to(fanoutExchange);
    }

    @Bean
    public FanoutExchange deletingMessageExchange() {
        return new FanoutExchange("org.asi.chatmessagesservice.fanout.deleting");
    }

    @Bean
    public DeleteMessagesSender deleteMessagesSender(RabbitTemplate rabbitTemplate, FanoutExchange deletingMessageExchange) {
        return new DeleteMessagesSender(rabbitTemplate, deletingMessageExchange);
    }

}

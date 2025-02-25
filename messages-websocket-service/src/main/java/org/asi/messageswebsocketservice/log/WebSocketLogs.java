package org.asi.messageswebsocketservice.log;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;


@Profile("dev")
@Component
public class WebSocketLogs {

    private final WebSocketMessageBrokerStats webSocketMessageBrokerStats;

    public WebSocketLogs(WebSocketMessageBrokerStats webSocketMessageBrokerStats) {
        this.webSocketMessageBrokerStats = webSocketMessageBrokerStats;
    }

    @PostConstruct
    public void init() {
        webSocketMessageBrokerStats.setLoggingPeriod(30 * 1000);
    }


}

package jpi.supplyfellow.supplyfellowchatservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // This annotation enables WebSocket message handling.
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Configure a message broker that will be used to route messages from one client to another.
        registry.enableSimpleBroker("/topic");
        // Clients will subscribe to the destination prefix "/topic" to receive messages.

        registry.setApplicationDestinationPrefixes("/app");
        // Designates the "/app" prefix for messages that are bound for @MessageMapping-annotated methods.
    }
}

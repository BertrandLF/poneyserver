package com.ninja_squad.ponyserver.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;


/**
 * The Spring configuration of web sockets
 * @author JB Nizet
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    /**
     * Configures the websocket message broker with the path "/topic". The positions of every pony during a running
     * race are broadcasted to /race/[id of the race]. Clients interested in the positions of the ponies of the race
     * 12 must thus subscribe using <code>stompClient.subscribe('/race/12', callback)</code>.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/race");
        registry.setApplicationDestinationPrefixes("/api");
    }

    /**
     * Configures the websocket endpoint "/race" with SockJS support. Clients may thus connect using
     * <code>new SockJS("/[context-path]/race")</code>.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
    }
}

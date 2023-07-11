package com.ajaksmaniac.streamify.controller;

import com.ajaksmaniac.streamify.dto.UserDto;
import com.ajaksmaniac.streamify.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class WebSocketController extends TextWebSocketHandler {

    private UserService userService;
    private static final Set<WebSocketSession> sessions = new HashSet<>();

    @Autowired
    public WebSocketController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        // Kada dodje sesija, proveriti usera preko query parama
        // Odma mu posalti notifikacije koje su na cekanju

//        String userId = UriComponentsBuilder.fromUriString(session.getUri().toString()).build().getQueryParams().get("userId");
        Long userId = Long.valueOf(UriComponentsBuilder.fromUriString(session.getUri().toString()).build().getQueryParams().get("userId").get(0));

        UserDto user =  userService.getUserById(userId);

        log.info("User: " + user);


        sessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Handle incoming messages
        String receivedMessage = message.getPayload();
        // Process the message as needed
        // Send a response to the connected clients
        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(new TextMessage("Response message"));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("SESSION COUNT : " +sessions.size());
        sessions.remove(session);
    }

//    @KafkaListener(topics = "notification-topic")
//    public void handleKafkaMessage(String message) throws IOException {
//        // Process the Kafka message
//        // ...
//        log.info("SENDING KAFKA MESSAGE: " +message);
//
//        for (WebSocketSession webSocketSession : sessions) {
//            log.info(webSocketSession.toString());
//            webSocketSession.getUri();//pogledati ovo
//            if (webSocketSession.isOpen()) {
//                log.info("SEndiong messagsage");
//
//                webSocketSession.sendMessage(new TextMessage("Response message"));
//            }
//        }
//    }
}
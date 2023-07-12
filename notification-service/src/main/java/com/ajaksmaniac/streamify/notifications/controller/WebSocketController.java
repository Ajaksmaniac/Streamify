package com.ajaksmaniac.streamify.notifications.controller;


import com.ajaksmaniac.streamify.notifications.dto.NotificationDto;
import com.ajaksmaniac.streamify.notifications.dto.UserDto;
import com.ajaksmaniac.streamify.notifications.entity.UserEntity;
import com.ajaksmaniac.streamify.notifications.service.ChannelService;
import com.ajaksmaniac.streamify.notifications.service.NotificationService;
import com.ajaksmaniac.streamify.notifications.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.HashBiMap;
import com.thoughtworks.xstream.converters.time.LocalDateTimeConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriComponentsBuilder;
import com.google.common.collect.BiMap;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Component
@Controller
public class WebSocketController extends TextWebSocketHandler {

    private UserService userService;
    private NotificationService notificationService;

    @Autowired
    @Qualifier("channelServiceImplementation")
    private ChannelService channelService;
    private static final BidiMap<Long,WebSocketSession> sessions = new DualHashBidiMap<>();

    @Autowired
    public WebSocketController(UserService userService,NotificationService notificationService) {
        this.userService = userService;
        this.notificationService = notificationService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        // Kada dodje sesija, proveriti usera preko query parama
        // Odma mu posalti notifikacije koje su na cekanju

//        String userId = UriComponentsBuilder.fromUriString(session.getUri().toString()).build().getQueryParams().get("userId");
        Long userId = Long.valueOf(UriComponentsBuilder.fromUriString(session.getUri().toString()).build().getQueryParams().get("userId").get(0));

        UserDto user =  userService.getUserById(userId);

        log.info("User: " + user);

        sessions.put(user.getId(),session);
        List<NotificationDto>  notifications = notificationService.getUserNotifications(userId);
        // Serialize the list to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String notificationsJson = objectMapper.writeValueAsString(notifications);
        session.sendMessage(new TextMessage(notificationsJson));

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Handle incoming messages
        String receivedMessage = message.getPayload();
        // Process the message as needed
        // Send a response to the connected clients
        for (WebSocketSession webSocketSession : sessions.values()) {
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(new TextMessage("Response message"));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("SESSION COUNT : " +sessions.size());
        sessions.removeValue(session);
    }

    @KafkaListener(topics = "subscribe-notification")
    public void handleKafkaMessage(String message) throws IOException {
        // Process the Kafka message
        // ...
                log.info("RECEIVED MESSAGE" + message);

        Long channelId = Long.valueOf(message.split(";")[0]);
        String videoId = message.split(";")[1];

        String notificationContent = message.split(";")[2];
        List<UserEntity> subscribedUsers = channelService.getSubscribedUsers(channelId);
        log.info("USERS_" +subscribedUsers.size());
        subscribedUsers.forEach(userEntity -> {
            log.info("SUBSCRIBED USER" +userEntity.getId());
//            = new NotificationDto(channelId,userEntity.getId(), notificationContent, LocalDateTime.now());

            NotificationDto notification = notificationService.save(notificationContent,userEntity.getId(), "/video/"+videoId);
            if(sessions.containsKey(userEntity.getId())){
                try {
                    log.info("SENDING KAFKA MESSAGE: " +notification);
                    ObjectMapper objectMapper = new ObjectMapper();
                    String notificationsJson = objectMapper.writeValueAsString(notification);
                    sessions.get(userEntity.getId()).sendMessage(new TextMessage(notificationsJson));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
}

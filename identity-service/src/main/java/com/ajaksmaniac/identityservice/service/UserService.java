package com.ajaksmaniac.identityservice.service;

import com.ajaksmaniac.identityservice.dto.UserDto;

import javax.management.Notification;
import java.util.List;

public interface UserService {

    UserDto getUserById(Long id);
//    void subscribeToChannel(Long userId, Long channelId);
//    void unsubscribeFromChannel(Long userId, Long channelId);
//    List<Notification> getUserNotifications(Long userId);

}

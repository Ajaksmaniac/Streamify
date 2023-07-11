package com.ajaksmaniac.streamify.service;

import com.ajaksmaniac.streamify.dto.ChannelDto;
import com.ajaksmaniac.streamify.dto.UserDto;
import com.ajaksmaniac.streamify.entity.ChannelEntity;

import javax.management.Notification;
import java.util.List;

public interface UserService {

    UserDto getUserById(Long id);
//    void subscribeToChannel(Long userId, Long channelId);
//    void unsubscribeFromChannel(Long userId, Long channelId);
//    List<Notification> getUserNotifications(Long userId);

}

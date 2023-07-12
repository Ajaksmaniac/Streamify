package com.ajaksmaniac.streamify.notifications.service;

import com.ajaksmaniac.streamify.notifications.entity.UserEntity;

import java.util.List;

public interface ChannelService {
    List<UserEntity> getSubscribedUsers(Long channelId);
}

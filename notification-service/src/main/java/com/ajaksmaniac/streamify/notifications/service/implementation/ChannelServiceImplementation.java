package com.ajaksmaniac.streamify.notifications.service.implementation;

import com.ajaksmaniac.streamify.notifications.entity.UserEntity;
import com.ajaksmaniac.streamify.notifications.repository.ChannelRepository;
import com.ajaksmaniac.streamify.notifications.service.ChannelService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ChannelServiceImplementation implements ChannelService {

    @Autowired
    ChannelRepository channelRepository;

    @Override
    public List<UserEntity> getSubscribedUsers(Long channelId) {

        return channelRepository.findById(channelId).orElseThrow().getSubscribers();
    }
}

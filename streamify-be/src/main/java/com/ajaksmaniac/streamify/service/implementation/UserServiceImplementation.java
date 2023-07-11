package com.ajaksmaniac.streamify.service.implementation;

import com.ajaksmaniac.streamify.dto.UserDto;
import com.ajaksmaniac.streamify.entity.ChannelEntity;
import com.ajaksmaniac.streamify.entity.UserEntity;
import com.ajaksmaniac.streamify.exception.channel.ChannelNotFoundException;
import com.ajaksmaniac.streamify.exception.user.AlreadySubscribedException;
import com.ajaksmaniac.streamify.exception.user.NotSubscribedException;
import com.ajaksmaniac.streamify.exception.user.UserNotExistentException;
import com.ajaksmaniac.streamify.mapper.UserMapper;
import com.ajaksmaniac.streamify.repository.ChannelRepository;
import com.ajaksmaniac.streamify.repository.UserRepository;
import com.ajaksmaniac.streamify.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Notification;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImplementation implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ChannelRepository channelRepository;
    @Autowired
    UserMapper userMapper;


    @Override
    public UserDto getUserById(Long id) {
        Optional< UserEntity > user = Optional.ofNullable(userRepository.findById(id).orElseThrow(() ->
                new UserNotExistentException(String.valueOf(id))));
        return userMapper.convertToDto(user.get());
    }

//    @Override
//    public void subscribeToChannel(Long userId, Long channelId){
//        Optional< UserEntity > user = Optional.ofNullable(userRepository.findById(userId).orElseThrow(() ->
//                new UserNotExistentException(String.valueOf(userId))));
//
//        Optional< ChannelEntity > channel = Optional.ofNullable(channelRepository.findById(channelId).orElseThrow(() ->
//                new ChannelNotFoundException(channelId)));
//        if(user.get().getSubscribedChannels().contains(channel.get())){
//            throw new AlreadySubscribedException(channel.get().getChannelName());
//        }
//        user.get().subscribe(channel.get());
//        userRepository.save(user.get());
//
//
//    }

//    @Override
//    public void unsubscribeFromChannel(Long userId, Long channelId){
//        Optional< UserEntity > user = Optional.ofNullable(userRepository.findById(userId).orElseThrow(() ->
//                new UserNotExistentException(String.valueOf(userId))));
//
//        Optional< ChannelEntity > channel = Optional.ofNullable(channelRepository.findById(channelId).orElseThrow(() ->
//                new ChannelNotFoundException(channelId)));
//        if(!user.get().getSubscribedChannels().contains(channel.get())){
//            throw new NotSubscribedException(channel.get().getChannelName());
//        }
//        user.get().unsubscribe(channel.get());
//        userRepository.save(user.get());
//
//
//
//    }

//    @Override
//    public List<Notification> getUserNotifications(Long userId) {
//
//        return null;
//    }
}

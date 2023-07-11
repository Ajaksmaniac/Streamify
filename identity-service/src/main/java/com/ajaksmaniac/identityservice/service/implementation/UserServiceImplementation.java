package com.ajaksmaniac.identityservice.service.implementation;

import com.ajaksmaniac.identityservice.dto.UserDto;
import com.ajaksmaniac.identityservice.entity.UserEntity;

import com.ajaksmaniac.identityservice.exception.user.UserNotExistentException;
import com.ajaksmaniac.identityservice.mapper.UserMapper;
import com.ajaksmaniac.identityservice.repository.UserRepository;
import com.ajaksmaniac.identityservice.service.UserService;
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
//    @Autowired
//    ChannelRepository channelRepository;
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

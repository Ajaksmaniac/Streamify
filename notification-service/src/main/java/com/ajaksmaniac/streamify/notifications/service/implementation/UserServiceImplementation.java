package com.ajaksmaniac.streamify.notifications.service.implementation;


import com.ajaksmaniac.streamify.notifications.dto.UserDto;
import com.ajaksmaniac.streamify.notifications.entity.ChannelEntity;
import com.ajaksmaniac.streamify.notifications.entity.UserEntity;

import com.ajaksmaniac.streamify.notifications.mapper.UserMapper;
import com.ajaksmaniac.streamify.notifications.repository.UserRepository;
import com.ajaksmaniac.streamify.notifications.service.UserService;
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
    UserMapper userMapper;


    @Override
    public UserDto getUserById(Long id) {
       UserEntity  user = userRepository.findById(id).get();

        userRepository.findById(id);
        return userMapper.convertToDto(user);
    }

}

package com.ajaksmaniac.streamify.service.implementation;

import com.ajaksmaniac.streamify.dto.UserDto;
import com.ajaksmaniac.streamify.entity.UserEntity;
import com.ajaksmaniac.streamify.exception.user.UserNotExistentException;
import com.ajaksmaniac.streamify.mapper.UserMapper;
import com.ajaksmaniac.streamify.repository.UserRepository;
import com.ajaksmaniac.streamify.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Optional< UserEntity > user = Optional.ofNullable(userRepository.findById(id).orElseThrow(() ->
                new UserNotExistentException(String.valueOf(id))));
        return userMapper.convertToDto(user.get());
    }
}

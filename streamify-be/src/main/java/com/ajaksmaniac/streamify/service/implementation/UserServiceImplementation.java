package com.ajaksmaniac.streamify.service.implementation;

import com.ajaksmaniac.streamify.entity.UserEntity;
import com.ajaksmaniac.streamify.exception.UserAlreadyExistsException;
import com.ajaksmaniac.streamify.exception.UserNotExistantException;
import com.ajaksmaniac.streamify.exception.VideoNotFoundException;
import com.ajaksmaniac.streamify.repository.UserRepository;
import com.ajaksmaniac.streamify.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImplementation implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public void saveUser(UserEntity user) {
        if(userRepository.existsByUsername(user.getUsername())){
            throw new UserAlreadyExistsException();
        }
        userRepository.save(user);
    }

    @Override
    public UserEntity getUser(String username) {
        if(!userRepository.existsByUsername(username)){
            throw new UserNotExistantException();
        }
        UserEntity user = userRepository.findByUsername(username);
        return user;
    }
}

package com.ajaksmaniac.streamify.service;

import com.ajaksmaniac.streamify.entity.RoleEntity;
import com.ajaksmaniac.streamify.entity.UserEntity;
import com.ajaksmaniac.streamify.exception.user.UserAlreadyExistsException;
import com.ajaksmaniac.streamify.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class UserManager implements UserDetailsManager {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void createUser(UserDetails user) {
        if(userRepository.existsByUsername(user.getUsername())){
            throw new UserAlreadyExistsException(user.getUsername());
        }
        ((UserEntity) user).setPassword(passwordEncoder.encode(user.getPassword()));
        ((UserEntity) user).setRole(new RoleEntity(1L, "user"));
        ((UserEntity) user).setActive(true);


        userRepository.save((UserEntity) user);
    }

    @Override
    public void updateUser(UserDetails user) {
       UserEntity entity = userRepository.findByUsername(user.getUsername()).orElseThrow(() ->
                new UsernameNotFoundException(MessageFormat.format("username {0} not found", user.getUsername())));

        entity.setUsername(user.getUsername());
        entity.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save((UserEntity) user);

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }


    @Override
    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(MessageFormat.format("username {0} not found", username)));
    }


}

package com.ajaksmaniac.streamify.service;

import com.ajaksmaniac.streamify.entity.UserEntity;

public interface UserService {

    public void saveUser(UserEntity user);
    public UserEntity getUser(String username);

}

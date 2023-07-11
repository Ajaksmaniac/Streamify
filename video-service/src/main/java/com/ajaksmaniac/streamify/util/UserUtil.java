package com.ajaksmaniac.streamify.util;


import com.ajaksmaniac.streamify.entity.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
public class UserUtil{

//    public UserEntity getSessionUser(UserEntity user){
//        return this.user;
//    }
    public boolean isUserAdmin(UserEntity user){
        return user.getRole().getName().equals("admin");
    }

    public boolean isUserContentCreator(UserEntity user){
        return user.getRole().getName().equals("content_creator");
    }
}

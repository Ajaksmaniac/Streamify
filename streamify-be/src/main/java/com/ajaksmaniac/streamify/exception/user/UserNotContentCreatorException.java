package com.ajaksmaniac.streamify.exception.user;


import com.ajaksmaniac.streamify.exception.CustomException;

public class UserNotContentCreatorException extends CustomException {

    public UserNotContentCreatorException(Long id) {
        super(id);
    }
}

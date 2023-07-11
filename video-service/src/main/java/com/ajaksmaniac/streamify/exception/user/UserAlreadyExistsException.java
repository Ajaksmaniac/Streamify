package com.ajaksmaniac.streamify.exception.user;

import com.ajaksmaniac.streamify.exception.CustomException;


public class UserAlreadyExistsException extends CustomException {
    public UserAlreadyExistsException(String name) {
        super(name);
    }
}

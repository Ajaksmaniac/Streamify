package com.ajaksmaniac.identityservice.exception.user;

import com.ajaksmaniac.identityservice.exception.CustomException;


public class UserAlreadyExistsException extends CustomException {
    public UserAlreadyExistsException(String name) {
        super(name);
    }
}

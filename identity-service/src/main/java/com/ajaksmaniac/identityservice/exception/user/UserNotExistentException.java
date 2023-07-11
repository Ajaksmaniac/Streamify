package com.ajaksmaniac.identityservice.exception.user;

import com.ajaksmaniac.identityservice.exception.CustomException;

public class UserNotExistentException extends CustomException {
    public UserNotExistentException(String name) {
        super(name);
    }
}

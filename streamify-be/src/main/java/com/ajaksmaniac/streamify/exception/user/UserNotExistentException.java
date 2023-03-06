package com.ajaksmaniac.streamify.exception.user;

import com.ajaksmaniac.streamify.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserNotExistentException extends CustomException {
    public UserNotExistentException(String name) {
        super(name);
    }
}

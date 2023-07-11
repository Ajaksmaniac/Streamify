package com.ajaksmaniac.streamify.exception.user;

import com.ajaksmaniac.streamify.exception.CustomException;

public class AlreadySubscribedException extends CustomException {
    public AlreadySubscribedException(String name) {
        super(name);
    }
}

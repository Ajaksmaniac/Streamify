package com.ajaksmaniac.streamify.exception.user;

import com.ajaksmaniac.streamify.exception.CustomException;

public class NotSubscribedException extends CustomException {
    public NotSubscribedException(String name) {
        super(name);
    }
}

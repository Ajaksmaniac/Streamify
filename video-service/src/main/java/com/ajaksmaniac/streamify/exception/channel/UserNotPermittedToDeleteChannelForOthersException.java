package com.ajaksmaniac.streamify.exception.channel;

import com.ajaksmaniac.streamify.exception.CustomException;


public class UserNotPermittedToDeleteChannelForOthersException extends CustomException {
    public UserNotPermittedToDeleteChannelForOthersException(Long id) {
        super(id);
    }
}

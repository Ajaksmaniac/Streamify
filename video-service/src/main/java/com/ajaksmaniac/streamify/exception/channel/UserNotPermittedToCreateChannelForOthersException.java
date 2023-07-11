package com.ajaksmaniac.streamify.exception.channel;

import com.ajaksmaniac.streamify.exception.CustomException;

public class UserNotPermittedToCreateChannelForOthersException extends CustomException {
    public UserNotPermittedToCreateChannelForOthersException(Long id) {
        super(id);
    }
}

package com.ajaksmaniac.streamify.exception.channel;


import com.ajaksmaniac.streamify.exception.CustomException;

public class UserNotPermittedToUpdateChannelForOthersException extends CustomException {
    public UserNotPermittedToUpdateChannelForOthersException(Long id) {
        super(id);
    }
}

package com.ajaksmaniac.streamify.exception.channel;

import com.ajaksmaniac.streamify.exception.CustomException;

public class ChannelAlreadyExistsException extends CustomException {

    public ChannelAlreadyExistsException(String name){
        super(name);
    }
}

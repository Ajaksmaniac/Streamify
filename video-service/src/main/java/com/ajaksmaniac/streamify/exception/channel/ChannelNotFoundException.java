package com.ajaksmaniac.streamify.exception.channel;

import com.ajaksmaniac.streamify.exception.CustomException;


public class ChannelNotFoundException extends CustomException {
    public ChannelNotFoundException(Long id){
        super(id);
    }
}

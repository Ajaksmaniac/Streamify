package com.ajaksmaniac.streamify.exception.video;


import com.ajaksmaniac.streamify.exception.CustomException;

public class UserNotPermittedToUpdateVideoException extends CustomException {
    public UserNotPermittedToUpdateVideoException(Long id) {
        super(id);
    }
}

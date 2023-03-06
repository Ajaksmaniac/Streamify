package com.ajaksmaniac.streamify.exception.video;


import com.ajaksmaniac.streamify.exception.CustomException;
public class UserNotPermittedToDeleteVideoException extends CustomException {
    public UserNotPermittedToDeleteVideoException(Long id) {
        super(id);
    }
}

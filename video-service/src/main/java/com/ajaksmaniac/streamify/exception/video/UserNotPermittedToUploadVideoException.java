package com.ajaksmaniac.streamify.exception.video;


import com.ajaksmaniac.streamify.exception.CustomException;

public class UserNotPermittedToUploadVideoException extends CustomException {
    public UserNotPermittedToUploadVideoException(Long id) {
        super(id);
    }
}

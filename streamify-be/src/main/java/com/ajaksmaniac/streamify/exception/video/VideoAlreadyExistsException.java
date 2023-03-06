package com.ajaksmaniac.streamify.exception.video;

import com.ajaksmaniac.streamify.exception.CustomException;

public class VideoAlreadyExistsException extends CustomException {

    public VideoAlreadyExistsException(String name) {
        super(name);
    }
}

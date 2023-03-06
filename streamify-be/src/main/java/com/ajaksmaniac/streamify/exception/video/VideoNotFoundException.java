package com.ajaksmaniac.streamify.exception.video;

import com.ajaksmaniac.streamify.exception.CustomException;

public class VideoNotFoundException extends CustomException {
    public VideoNotFoundException(Long id) {
        super(id);
    }
}

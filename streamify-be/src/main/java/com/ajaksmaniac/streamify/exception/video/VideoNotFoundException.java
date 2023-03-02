package com.ajaksmaniac.streamify.exception.video;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class VideoNotFoundException extends RuntimeException{
}

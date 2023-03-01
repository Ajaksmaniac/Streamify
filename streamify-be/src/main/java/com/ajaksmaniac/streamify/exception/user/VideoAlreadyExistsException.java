package com.ajaksmaniac.streamify.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class VideoAlreadyExistsException extends RuntimeException{
}

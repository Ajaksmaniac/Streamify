package com.ajaksmaniac.streamify.exception.video;


import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class UserNotPermittedToUpdateVideoException extends RuntimeException{
}

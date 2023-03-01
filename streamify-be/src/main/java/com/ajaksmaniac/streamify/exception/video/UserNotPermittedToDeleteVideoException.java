package com.ajaksmaniac.streamify.exception.video;


import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class UserNotPermittedToDeleteVideoException extends RuntimeException{
}

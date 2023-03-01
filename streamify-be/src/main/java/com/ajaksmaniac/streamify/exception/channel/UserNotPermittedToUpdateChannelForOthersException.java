package com.ajaksmaniac.streamify.exception.channel;


import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class UserNotPermittedToUpdateChannelForOthersException extends RuntimeException{
}

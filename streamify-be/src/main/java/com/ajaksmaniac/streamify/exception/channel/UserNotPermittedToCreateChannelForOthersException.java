package com.ajaksmaniac.streamify.exception.channel;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus
public class UserNotPermittedToCreateChannelForOthersException extends RuntimeException{
}

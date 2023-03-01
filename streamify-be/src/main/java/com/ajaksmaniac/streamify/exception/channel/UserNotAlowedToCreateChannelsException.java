package com.ajaksmaniac.streamify.exception.channel;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "This user is not allowed to create channels!")
public class UserNotAlowedToCreateChannelsException extends RuntimeException{
}

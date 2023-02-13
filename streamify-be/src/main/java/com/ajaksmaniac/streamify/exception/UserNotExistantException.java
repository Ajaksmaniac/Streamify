package com.ajaksmaniac.streamify.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "A user with this username doesn't exist")
public class UserNotExistantException extends RuntimeException{
}

package com.ajaksmaniac.streamify.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "A user with this username already exists")
public class UserAlreadyExistsException extends RuntimeException {
}

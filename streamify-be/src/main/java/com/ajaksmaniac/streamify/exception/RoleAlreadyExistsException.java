package com.ajaksmaniac.streamify.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Role with this name already exist")
public class RoleAlreadyExistsException extends RuntimeException{
}

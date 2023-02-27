package com.ajaksmaniac.streamify.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "A Channel with this id or name doesn't exist")
public class ChannelNotFoundException extends RuntimeException{
}

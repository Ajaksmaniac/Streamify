package com.ajaksmaniac.streamify.exception.comment;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus
public class CommentNotFoundException extends RuntimeException{
}

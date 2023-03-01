package com.ajaksmaniac.streamify.exception.comment;

import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus
public class UserNotPermittedToDeleteOthersCommentsException extends RuntimeException{
}

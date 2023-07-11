package com.ajaksmaniac.streamify.exception.comment;

import com.ajaksmaniac.streamify.exception.CustomException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.ResponseStatus;



public class UserNotPermittedToDeleteOthersCommentsException extends CustomException {

    public UserNotPermittedToDeleteOthersCommentsException(Long id) {
        super(id);
    }
}

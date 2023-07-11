package com.ajaksmaniac.streamify.exception.comment;

import com.ajaksmaniac.streamify.exception.CustomException;

public class CommentNotFoundException extends CustomException {

    public CommentNotFoundException(Long id) {
        super(id);
    }
}

package com.ajaksmaniac.streamify.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

//@AllArgsConstructor
@Getter
public abstract class CustomException extends RuntimeException {
    private Long id;
    private String name;

    public CustomException(Long id) {
        this.id = id;
    }

    public CustomException(String name) {
        this.name = name;
    }
}

package com.ajaksmaniac.identityservice.exception;

import com.ajaksmaniac.identityservice.exception.user.UserAlreadyExistsException;
import com.ajaksmaniac.identityservice.exception.user.UserNotExistentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.net.URISyntaxException;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(UserNotExistentException.class)
    ProblemDetail handleUserNotExistentException(UserNotExistentException e) throws URISyntaxException  {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, String.format("User with username or id '%s' doesn't exist.", e.getName()));
        problemDetail.setTitle("User not found");
        problemDetail.setType(new URI("Not-Found"));
        return problemDetail;
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    ProblemDetail handleAlreadyExistsException(UserAlreadyExistsException e) throws URISyntaxException  {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, String.format("User with username '%s' already exists.", e.getName()));
        problemDetail.setTitle("User already exists");
        problemDetail.setType(new URI("CONFLICT"));
        return problemDetail;
    }

}
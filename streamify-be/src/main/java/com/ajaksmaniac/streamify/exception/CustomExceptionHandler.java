package com.ajaksmaniac.streamify.exception;


import com.ajaksmaniac.streamify.exception.channel.*;
import com.ajaksmaniac.streamify.exception.comment.CommentNotFoundException;
import com.ajaksmaniac.streamify.exception.comment.UserNotPermittedToDeleteOthersCommentsException;
import com.ajaksmaniac.streamify.exception.user.*;
import com.ajaksmaniac.streamify.exception.video.*;
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

    @ExceptionHandler(ChannelNotFoundException.class)
    ProblemDetail handleChannelNotFoundException(ChannelNotFoundException e) throws URISyntaxException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, String.format("Chanel with this id '%d' doesn't exist.",e.getId()));
        problemDetail.setTitle("Channel Not Found");
        problemDetail.setType(new URI("Not-Found"));
        return problemDetail;
    }

    @ExceptionHandler(ChannelAlreadyExistsException.class)
    ProblemDetail handleChannelAlreadyExistsException(ChannelAlreadyExistsException e) throws URISyntaxException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, String.format("Chanel with name '%s' already exists.",e.getName()));
        problemDetail.setTitle("Channel Already Exists");
        problemDetail.setType(new URI("Conflict"));
        return problemDetail;
    }

    @ExceptionHandler(VideoNotFoundException.class)
    ProblemDetail handleVideoNotFoundException(VideoNotFoundException e) throws URISyntaxException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, String.format("Video with id '%d' doesn't exist.",e.getId()));
        problemDetail.setTitle("Video Not Found");
        problemDetail.setType(new URI("Not-Found"));
        return problemDetail;
    }

    @ExceptionHandler(VideoAlreadyExistsException.class)
    ProblemDetail handleVideoAlreadyExistsException(VideoAlreadyExistsException e) throws URISyntaxException  {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, String.format("Video with name '%s' already exists",e.getName()));
        problemDetail.setTitle("Video already ecists");
        problemDetail.setType(new URI("Conflict"));
        return problemDetail;

    }

    @ExceptionHandler(UserNotPermittedToDeleteVideoException.class)
    ProblemDetail handleUserNotPermittedToDeleteVideoException(UserNotPermittedToDeleteVideoException e) throws URISyntaxException  {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, String.format("User with id '%d' not Permitted to delete others videos",e.getId()));
        problemDetail.setTitle("User cant delete videos on channels not owned by him");
        problemDetail.setType(new URI("Not-Acceptable"));

        return problemDetail;

    }

    @ExceptionHandler(UserNotPermittedToUploadVideoException.class)
    ProblemDetail handleUserNotPermittedToUploadVideoException(UserNotPermittedToUploadVideoException e) throws URISyntaxException  {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, String.format("User with id '%d' not Permitted to upload videos on channels not owned by that users.",e.getId()));
        problemDetail.setTitle("User cant upload videos on channels not owned by him");
        problemDetail.setType(new URI("Not-Acceptable"));

        return problemDetail;

    }

    @ExceptionHandler(UserNotPermittedToUpdateVideoException.class)
    ProblemDetail handleUserNotPermittedToUpdateVideoException(UserNotPermittedToUpdateVideoException e) throws URISyntaxException  {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, String.format("User with id '%d' not Permitted to update videos on channels not owned by that user.",e.getId()));
        problemDetail.setTitle("User cant updated videos on channels not owned by him");
        problemDetail.setType(new URI("Not-Allowed"));

        return problemDetail;

    }

    @ExceptionHandler(UserNotPermittedToCreateChannelForOthersException.class)
    ProblemDetail handleUserNotPermittedToCreateChannelForOthersException(UserNotPermittedToCreateChannelForOthersException e) throws URISyntaxException  {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, String.format("User with id '%d' not Permitted to create channels for others", e.getId()));
        problemDetail.setTitle("User not allowed to create channels for others");
        problemDetail.setType(new URI("Not-Allowed"));

        return problemDetail;

    }

    @ExceptionHandler(UserNotPermittedToDeleteChannelForOthersException.class)
    ProblemDetail handleUserNotPermittedToDeleteChannelForOthersException(UserNotPermittedToDeleteChannelForOthersException e) throws URISyntaxException  {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, String.format("User with id '%d' not Permitted to delete channels for others",e.getId()));
        problemDetail.setTitle("Users can delete each other channels.");
        problemDetail.setType(new URI("Not-Allowed"));

        return problemDetail;

    }

    @ExceptionHandler(UserNotPermittedToUpdateChannelForOthersException.class)
    ProblemDetail handleUserNotPermittedToUpdateChannelForOthersException(UserNotPermittedToUpdateChannelForOthersException e) throws URISyntaxException  {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, String.format("User with id '%d' not Permitted to update channels for others",e.getId()));
        problemDetail.setTitle("Users cant Updated channels for others.");
        problemDetail.setType(new URI("Not-Allowed"));

        return problemDetail;

    }

    @ExceptionHandler(UserNotPermittedToDeleteOthersCommentsException.class)
    ProblemDetail handleUserNotPermittedToOthersCommentsException(UserNotPermittedToDeleteOthersCommentsException e) throws URISyntaxException  {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, String.format("User with id '%d' not Permitted to delete others Comments",e.getId()));
        problemDetail.setTitle("User Not Permitted to delete comments");
        problemDetail.setType(new URI("Not-Allowed"));

        return problemDetail;

    }

    @ExceptionHandler({CommentNotFoundException.class,})
    ProblemDetail handleCommentNotFoundException(CommentNotFoundException e) throws URISyntaxException  {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, String.format("Comment With id '%d' doesn't exists!",e.getId()));
        problemDetail.setTitle("Comment Not Found");
        problemDetail.setType(new URI("Not-Found"));

        return problemDetail;

    }

    @ExceptionHandler(UserNotContentCreatorException.class)
    ProblemDetail handleUserNotContentCreatorException(UserNotContentCreatorException e) throws URISyntaxException  {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, String.format("User with id '%d' is not content creator",e.getId()));
        problemDetail.setTitle("Only content creator can Manage videos");
        problemDetail.setType(new URI("Not-Allowed"));
        return problemDetail;

    }

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

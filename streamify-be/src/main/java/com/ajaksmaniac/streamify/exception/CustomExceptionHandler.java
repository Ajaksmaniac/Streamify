package com.ajaksmaniac.streamify.exception;


import com.ajaksmaniac.streamify.exception.channel.*;
import com.ajaksmaniac.streamify.exception.comment.CommentNotFoundException;
import com.ajaksmaniac.streamify.exception.comment.UserNotPermittedToDeleteOthersCommentsException;
import com.ajaksmaniac.streamify.exception.user.*;
import com.ajaksmaniac.streamify.exception.video.UserNotPermittedToDeleteVideoException;
import com.ajaksmaniac.streamify.exception.video.UserNotPermittedToUpdateVideoException;
import com.ajaksmaniac.streamify.exception.video.UserNotPermittedToUploadVideoException;
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
    ProblemDetail handleChannelNotFoundException() throws URISyntaxException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Chanel with this id or name doesn't exist.");
        problemDetail.setTitle("Channel Not Found");
        problemDetail.setType(new URI("Not-Found"));
        return problemDetail;
    }

    @ExceptionHandler(ChannelAlreadyExistsException.class)
    ProblemDetail handleChannelAlreadyExistsException() throws URISyntaxException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "Chanel with this id or name already exist.");
        problemDetail.setTitle("Channel Already Exists");
        problemDetail.setType(new URI("Conflict"));
        return problemDetail;
    }

    @ExceptionHandler(VideoNotFoundException.class)
    ProblemDetail handleVideoNotFoundException() throws URISyntaxException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Video with this id or name doesn't exist.");
        problemDetail.setTitle("Video Not Found");
        problemDetail.setType(new URI("Not-Found"));
        return problemDetail;
    }

    @ExceptionHandler(VideoAlreadyExistsException.class)
    ProblemDetail handleVideoAlreadyExistsException() throws URISyntaxException  {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "Video with this name already exists");
        problemDetail.setTitle("Duplicate video");
        problemDetail.setType(new URI("Conflict"));
        return problemDetail;

    }

    @ExceptionHandler(UserNotPermittedToDeleteVideoException.class)
    ProblemDetail handleUserNotPermittedToDeleteVideoException() throws URISyntaxException  {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, "User not Permitted to delete others videos");
        problemDetail.setTitle("Not Permitted");
        problemDetail.setType(new URI("Not-Acceptable"));

        return problemDetail;

    }

    @ExceptionHandler(UserNotPermittedToUploadVideoException.class)
    ProblemDetail handleUserNotPermittedToUploadVideoException() throws URISyntaxException  {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, "User not Permitted to upload videos on channels not owned by that users.");
        problemDetail.setTitle("Not Permitted");
        problemDetail.setType(new URI("Not-Acceptable"));

        return problemDetail;

    }

    @ExceptionHandler(UserNotPermittedToUpdateVideoException.class)
    ProblemDetail handleUserNotPermittedToUpdateVideoException() throws URISyntaxException  {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, "User not Permitted to update videos on channels not owned by that users.");
        problemDetail.setTitle("Not Permitted");
        problemDetail.setType(new URI("Not-Allowed"));

        return problemDetail;

    }

    @ExceptionHandler(UserNotPermittedToCreateChannelForOthersException.class)
    ProblemDetail handleUserNotPermittedToCreateChannelForOthersException() throws URISyntaxException  {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, "User not Permitted to create channels for others");
        problemDetail.setTitle("Not Permitted");
        problemDetail.setType(new URI("Not-Allowed"));

        return problemDetail;

    }

    @ExceptionHandler(UserNotPermittedToDeleteChannelForOthersException.class)
    ProblemDetail handleUserNotPermittedToDeleteChannelForOthersException() throws URISyntaxException  {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, "User not Permitted to delete channels for others");
        problemDetail.setTitle("Not Permitted");
        problemDetail.setType(new URI("Not-Allowed"));

        return problemDetail;

    }

    @ExceptionHandler(UserNotPermittedToUpdateChannelForOthersException.class)
    ProblemDetail handleUserNotPermittedToUpdateChannelForOthersException() throws URISyntaxException  {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, "User not Permitted to update channels for others");
        problemDetail.setTitle("Not Permitted");
        problemDetail.setType(new URI("Not-Allowed"));

        return problemDetail;

    }

    @ExceptionHandler(UserNotPermittedToDeleteOthersCommentsException.class)
    ProblemDetail handleUserNotPermittedToOthersCommentsException() throws URISyntaxException  {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, "User not Permitted to delete others Comments");
        problemDetail.setTitle("Not Permitted");
        problemDetail.setType(new URI("Not-Allowed"));

        return problemDetail;

    }

    @ExceptionHandler(CommentNotFoundException.class)
    ProblemDetail handleCommentNotFoundException() throws URISyntaxException  {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Comment With this id not found");
        problemDetail.setTitle("Comment Not Found");
        problemDetail.setType(new URI("Not-Found"));

        return problemDetail;

    }

    @ExceptionHandler(UserNotContentCreatorException.class)
    ProblemDetail handleUserNotContentCreatorException() throws URISyntaxException  {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, "Only content creator can Manage videos");
        problemDetail.setTitle("Not Permitted");
        problemDetail.setType(new URI("Not-Allowed"));

        return problemDetail;

    }
}

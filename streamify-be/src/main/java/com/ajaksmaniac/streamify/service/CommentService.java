package com.ajaksmaniac.streamify.service;

import com.ajaksmaniac.streamify.dto.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto getComment(Long id);

    CommentDto saveComment(CommentDto comment);

    CommentDto updateComment(CommentDto comment);
    List<CommentDto> getCommentsForVideo(Long videoId);
    void deleteById(Long id);



}

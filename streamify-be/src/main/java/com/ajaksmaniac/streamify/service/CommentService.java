package com.ajaksmaniac.streamify.service;

import com.ajaksmaniac.streamify.dto.CommentDto;
import com.ajaksmaniac.streamify.entity.CommentEntity;
import com.ajaksmaniac.streamify.entity.VideoEntity;

import java.util.List;

public interface CommentService {

    CommentDto getComment(Long id);

    void saveComment(CommentDto comment);
    List<CommentDto> getCommentsForVideo(Long videoId);
    void deleteById(Long id);



}

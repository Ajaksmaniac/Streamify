package com.ajaksmaniac.streamify.service;

import com.ajaksmaniac.streamify.entity.CommentEntity;
import com.ajaksmaniac.streamify.entity.VideoEntity;

import java.util.List;

public interface CommentService {

    CommentEntity getComment(Long id);

    void saveComment(CommentEntity comment);
    List<CommentEntity> getCommentsForVideo(Long videoId);
    void deleteById(Long id);



}

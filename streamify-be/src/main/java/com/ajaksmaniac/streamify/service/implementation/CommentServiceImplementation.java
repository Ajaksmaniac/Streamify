package com.ajaksmaniac.streamify.service.implementation;

import com.ajaksmaniac.streamify.entity.CommentEntity;
import com.ajaksmaniac.streamify.entity.UserEntity;
import com.ajaksmaniac.streamify.entity.VideoEntity;
import com.ajaksmaniac.streamify.exception.CommentNotFoundException;
import com.ajaksmaniac.streamify.exception.UserNotExistantException;
import com.ajaksmaniac.streamify.exception.VideoNotFoundException;
import com.ajaksmaniac.streamify.repository.CommentRepository;
import com.ajaksmaniac.streamify.repository.UserRepository;
import com.ajaksmaniac.streamify.repository.VideoRepository;
import com.ajaksmaniac.streamify.service.CommentService;
import com.ajaksmaniac.streamify.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentServiceImplementation implements CommentService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    VideoRepository videoRepository;

    @Override
    public CommentEntity getComment(Long id) {
        if(!commentRepository.existsById(id)){
            throw new CommentNotFoundException();
        }

        return commentRepository.findByCommentId(id);
    }

    @Override
    public void saveComment(CommentEntity comment) {
        if(videoRepository.existsById((comment.getVideo().getId()))){
            throw new VideoNotFoundException();
        }

        if(userRepository.existsById((comment.getUser().getId()))){
            throw new UserNotExistantException();
        }

        UserEntity user = userRepository.findByUsername(comment.getUser().getUsername());
        VideoEntity video = videoRepository.findByName(comment.getVideo().getName());

        comment.setUser(user);
        comment.setVideo(video);

        commentRepository.save(comment);
    }

    @Override
    public List<CommentEntity> getCommentsForVideo(Long videoId) {
        if(videoRepository.existsById(videoId)){
            throw new VideoNotFoundException();
        }
        return commentRepository.findByMovieId(videoId);
    }


    @Override
    public void deleteById(Long id) {
        if(commentRepository.existsById(id)){
            throw new CommentNotFoundException();
        }
        commentRepository.deleteById(id);
    }
}

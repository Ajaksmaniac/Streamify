package com.ajaksmaniac.streamify.service.implementation;

import com.ajaksmaniac.streamify.dto.CommentDto;
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
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImplementation implements CommentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Override
    public CommentDto getComment(Long id) {
        if(!commentRepository.existsById(id)){
            throw new CommentNotFoundException();
        }
        CommentEntity en = commentRepository.findByCommentId(id);

        return new CommentDto(en.getId(), en.getContent(), en.getVideo().getId(),en.getUser().getId());
    }

    @Override
    public void saveComment(CommentDto comment) {
        if(!videoRepository.existsById((comment.getVideoId()))){
            throw new VideoNotFoundException();
        }

        if(!userRepository.existsById((comment.getUserId()))){
            throw new UserNotExistantException();
        }

        UserEntity user = userRepository.findByUserId(comment.getUserId());
        VideoEntity video = videoRepository.findByVideoId(comment.getVideoId());

        CommentEntity entity = new CommentEntity();
        entity.setContent(comment.getContent());
        entity.setUser(user);
        entity.setVideo(video);
        commentRepository.save(entity);
    }

    @Override
    public List<CommentDto> getCommentsForVideo(Long videoId) {
        if(!videoRepository.existsById(videoId)){
            throw new VideoNotFoundException();
        }

        List<CommentDto> list = commentRepository.findByMovieId(videoId).stream().map(o->{
            return new CommentDto(o.getId(),o.getContent(),o.getVideo().getId(),o.getUser().getId());
        }).collect(Collectors.toList());
        return list;
    }


    @Override
    public void deleteById(Long id) {
        if(!commentRepository.existsById(id)){
            throw new CommentNotFoundException();
        }
        commentRepository.deleteById(id);
    }
}

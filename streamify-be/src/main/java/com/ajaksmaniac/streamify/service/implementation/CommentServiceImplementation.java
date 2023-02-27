package com.ajaksmaniac.streamify.service.implementation;

import com.ajaksmaniac.streamify.dto.CommentDto;
import com.ajaksmaniac.streamify.entity.CommentEntity;
import com.ajaksmaniac.streamify.entity.UserEntity;
import com.ajaksmaniac.streamify.entity.VideoDetailsEntity;
import com.ajaksmaniac.streamify.exception.CommentNotFoundException;
import com.ajaksmaniac.streamify.exception.UserNotExistantException;
import com.ajaksmaniac.streamify.exception.VideoNotFoundException;
import com.ajaksmaniac.streamify.mapper.CommentMapper;
import com.ajaksmaniac.streamify.mapper.VideoDetailsMapper;
import com.ajaksmaniac.streamify.repository.CommentRepository;
import com.ajaksmaniac.streamify.repository.UserRepository;
import com.ajaksmaniac.streamify.repository.VideoRepository;
import com.ajaksmaniac.streamify.service.CommentService;
import com.nimbusds.jwt.util.DateUtils;
import lombok.AllArgsConstructor;
import org.hibernate.type.descriptor.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
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

    @Autowired
    private CommentMapper mapper;

    @Override
    public CommentDto getComment(Long id) {
        if(!commentRepository.existsById(id)){
            throw new CommentNotFoundException();
        }
        CommentEntity en = commentRepository.findByCommentId(id);

//        return new CommentDto(en.getId(), en.getContent(), en.getVideoDetails().getId(),en.getUser().getId());
        return mapper.convertToDto(en);
    }

    @Override
    public void saveComment(CommentDto comment) {
        if(!videoRepository.existsById((comment.getVideoId()))){
            throw new VideoNotFoundException();
        }

        if(!userRepository.existsByUsername((comment.getUsername()))){
            throw new UserNotExistantException();
        }

        UserEntity user = userRepository.findByUsername(comment.getUsername()).get();
        VideoDetailsEntity video = videoRepository.findById(comment.getVideoId()).get();

        CommentEntity entity = new CommentEntity();
        entity.setContent(comment.getContent());
        entity.setUser(user);
        entity.setVideoDetails(video);
        entity.setId(null);

        entity.setCommentedAt(Date.valueOf(LocalDate.now()));
        commentRepository.save(entity);
    }

    @Override
    public List<CommentDto> getCommentsForVideo(Long videoId) {
        if(!videoRepository.existsById(videoId)){
            throw new VideoNotFoundException();
        }

        List<CommentDto> list = commentRepository.findByMovieId(videoId).stream().map(o-> mapper.convertToDto(o)).collect(Collectors.toList());
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

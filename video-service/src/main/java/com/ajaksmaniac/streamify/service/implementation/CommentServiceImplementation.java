package com.ajaksmaniac.streamify.service.implementation;

import com.ajaksmaniac.streamify.dto.CommentDto;
import com.ajaksmaniac.streamify.entity.CommentEntity;
import com.ajaksmaniac.streamify.entity.UserEntity;
import com.ajaksmaniac.streamify.entity.VideoDetailsEntity;
import com.ajaksmaniac.streamify.exception.comment.CommentNotFoundException;
import com.ajaksmaniac.streamify.exception.comment.UserNotPermittedToDeleteOthersCommentsException;
import com.ajaksmaniac.streamify.exception.user.UserNotExistentException;
import com.ajaksmaniac.streamify.exception.video.VideoNotFoundException;
import com.ajaksmaniac.streamify.mapper.CommentMapper;
import com.ajaksmaniac.streamify.repository.CommentRepository;
import com.ajaksmaniac.streamify.repository.UserRepository;
import com.ajaksmaniac.streamify.repository.VideoRepository;
import com.ajaksmaniac.streamify.service.CommentService;
import com.ajaksmaniac.streamify.util.UserUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CommentServiceImplementation implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private CommentMapper mapper;

    @Autowired
    UserUtil userUtil;

    @Override
    public CommentDto getComment(Long id) {
        Optional<CommentEntity> en = commentRepository.findByCommentId(id);
        if (en.isEmpty()) {
            throw new CommentNotFoundException(id);
        }
        return mapper.convertToDto(en.get());
    }

    @Override
    public CommentDto saveComment(CommentDto comment,Long authenticatedUserId) {
        if (!videoRepository.existsById((comment.getVideoId()))) {
            throw new VideoNotFoundException(comment.getVideoId());
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDate date = now.toLocalDate();

        CommentEntity entity = mapper.convertToEntity(comment);
        entity.setCommentedAt(java.sql.Date.valueOf(date));


        entity.setUser(sessionUser(authenticatedUserId));
        entity.setVideoDetails(new VideoDetailsEntity(comment.getVideoId()));

        return mapper.convertToDto(commentRepository.save(entity));
    }

    @Override
    public List<CommentDto> getCommentsForVideo(Long videoId) {
        if (!videoRepository.existsById(videoId)) {
            throw new VideoNotFoundException(videoId);
        }

        return commentRepository.findByMovieId(videoId).stream().map(o -> mapper.convertToDto(o)).collect(Collectors.toList());
    }


    @Override
    public void deleteById(Long id,Long authenticatedUserId) {
        Optional<CommentEntity> en = commentRepository.findByCommentId(id);

        if (en.isEmpty()) {
            throw new CommentNotFoundException(id);
        }
        if (!userUtil.isUserAdmin(sessionUser(authenticatedUserId))) {

            // Is user owner of the comment
            if(!en.get().getUser().getId().equals(sessionUser(authenticatedUserId).getId())){
                //is User owner of the video
                if (!videoRepository.isVideoOwnedByUser(en.get().getVideoDetails().getId(), sessionUser(authenticatedUserId).getId())) {
                    throw new UserNotPermittedToDeleteOthersCommentsException(sessionUser(authenticatedUserId).getId());

                }
            }

        }

        commentRepository.deleteById(id);
    }

    private UserEntity sessionUser(Long userId) {
        Optional< UserEntity > user = Optional.ofNullable(userRepository.findById(userId).orElseThrow(() ->
                new UserNotExistentException(String.valueOf(userId))));
        return user.get();
    }
}

package com.ajaksmaniac.streamify.service.implementation;

import com.ajaksmaniac.streamify.dto.VideoDetailsDto;
import com.ajaksmaniac.streamify.entity.ChannelEntity;
import com.ajaksmaniac.streamify.entity.UserEntity;
import com.ajaksmaniac.streamify.entity.VideoDetailsEntity;
import com.ajaksmaniac.streamify.exception.channel.ChannelNotFoundException;
import com.ajaksmaniac.streamify.exception.user.UserNotContentCreatorException;
import com.ajaksmaniac.streamify.exception.user.VideoAlreadyExistsException;
import com.ajaksmaniac.streamify.exception.user.VideoNotFoundException;
import com.ajaksmaniac.streamify.exception.video.UserNotPermittedToDeleteVideoException;
import com.ajaksmaniac.streamify.exception.video.UserNotPermittedToUpdateVideoException;
import com.ajaksmaniac.streamify.exception.video.UserNotPermittedToUploadVideoException;
import com.ajaksmaniac.streamify.repository.ChannelRepository;
import com.ajaksmaniac.streamify.repository.CommentRepository;
import com.ajaksmaniac.streamify.repository.UserRepository;
import com.ajaksmaniac.streamify.repository.VideoRepository;
import com.ajaksmaniac.streamify.service.VideoService;
import com.ajaksmaniac.streamify.util.FileUtil;
import com.ajaksmaniac.streamify.util.UserUtil;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@AllArgsConstructor
@Slf4j
public class VideoServiceImplementation implements VideoService {

    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    UserUtil userUtil;




    @Override
    public Resource getVideo(Long id) throws IOException {

        if (!videoRepository.existsById(id)) {
            throw new VideoNotFoundException();
        }
        return new FileUtil().getFileAsResource(id.toString());

    }

    @Override
    public VideoDetailsDto getVideoDetails(Long id) {

        if (!videoRepository.existsById(id)) {
            throw new VideoNotFoundException();
        }

        VideoDetailsEntity videoDetailsEntity = videoRepository.findById(id).get();

        VideoDetailsDto dto = new VideoDetailsDto(videoDetailsEntity.getId(), videoDetailsEntity.getName(), videoDetailsEntity.getChannel().getChannelName(), "/video/id/" + videoDetailsEntity.getId());
        return dto;
    }

    @Override
    public List<VideoDetailsDto> getAllVideosDetails() {

        List<VideoDetailsDto> list = new ArrayList<>();

        videoRepository.getAllVideos().stream().forEach(v -> {
            list.add(new VideoDetailsDto(v.getId(), v.getName(), v.getChannel().getChannelName(), "/video/id/" + v.getId()));
        });

        return list;
    }

    @Override
    public void saveVideo(MultipartFile file, String name, String channelName, String description) throws IOException {

        if (!userUtil.isUserAdmin(sessionUser())) {
            if (!userUtil.isUserContentCreator(sessionUser()))
                throw new UserNotContentCreatorException();
            List<ChannelEntity> channels = channelRepository.findByUserId(sessionUser().getId());
            AtomicBoolean flag = new AtomicBoolean(false);
            channels.forEach(c -> {
                if (c.getChannelName().equals(channelName)) {
                    flag.set(true);
                }
            });
            if (!flag.get()) throw new UserNotPermittedToUploadVideoException();
        }

        if (videoRepository.existsByName(name)) {
            throw new VideoAlreadyExistsException();
        }

        if (!channelRepository.existsByChannelName(channelName)) {
            throw new ChannelNotFoundException();
        }

        ChannelEntity channel = channelRepository.findByChannelName(channelName.toLowerCase());

        LocalDateTime now = LocalDateTime.now();
        LocalDate date = now.toLocalDate();
        VideoDetailsEntity newVideoDetailsEntity = new VideoDetailsEntity(name, channel, java.sql.Date.valueOf(date), description);
        VideoDetailsEntity saved = videoRepository.save(newVideoDetailsEntity);
        saved.setVideoUrl("/video/id/" + saved.getId());
        saved.setDescription(description);
        saved.setPostedAt(java.sql.Date.valueOf(date));

        FileUtil.saveFile(saved.getId().toString(), name, file);

        videoRepository.save(saved);
    }

    @Override
    public List<String> getAllVideoNames() {
        return videoRepository.getAllEntryNames();
    }

    @Override
//    @Transactional
    public void deleteVideo(Long id) throws IOException {
        if (!videoRepository.existsById(id)) {
            throw new VideoNotFoundException();
        }
        VideoDetailsEntity video = videoRepository.findById(id).get();

        if (!userUtil.isUserAdmin(sessionUser())) {
            if (!userUtil.isUserContentCreator(sessionUser()))
                throw new UserNotContentCreatorException();
            List<ChannelEntity> channels = channelRepository.findByUserId(sessionUser().getId());
            AtomicBoolean flag = new AtomicBoolean(false);
            channels.forEach(c -> {
                if (video.getChannel().getId() == c.getId()) {
                    flag.set(true);
                }
            });
            if (!flag.get()) throw new UserNotPermittedToDeleteVideoException();
        }

        new FileUtil().deleteFile(id.toString());

        videoRepository.deleteById(id);

    }

    @Override
    public void updateVideo(Long id, String name, String description) {

        if (!videoRepository.existsById(id)) {
            throw new VideoNotFoundException();
        }

        VideoDetailsEntity video = videoRepository.findById(id).get();
        if (!userUtil.isUserAdmin(sessionUser())) {
            if (!userUtil.isUserContentCreator(sessionUser()))
                throw new UserNotContentCreatorException();
            List<ChannelEntity> channels = channelRepository.findByUserId(sessionUser().getId());
            AtomicBoolean flag = new AtomicBoolean(false);
            channels.forEach(c -> {
                if (video.getChannel().getId() == c.getId()) {
                    flag.set(true);
                }
            });
            if (!flag.get()) throw new UserNotPermittedToUpdateVideoException();
        }


        if (videoRepository.existsByName(name)) {
            throw new VideoAlreadyExistsException();
        }

        VideoDetailsEntity entity = videoRepository.findById(id).get();

        entity.setName(name);
        entity.setDescription(description);

        videoRepository.save(entity);
    }

    private UserEntity sessionUser() {
        return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

package com.ajaksmaniac.streamify.service.implementation;

import com.ajaksmaniac.streamify.dto.VideoDetailsDto;
import com.ajaksmaniac.streamify.entity.ChannelEntity;
import com.ajaksmaniac.streamify.entity.UserEntity;
import com.ajaksmaniac.streamify.entity.VideoDetailsEntity;
import com.ajaksmaniac.streamify.exception.channel.ChannelNotFoundException;
import com.ajaksmaniac.streamify.exception.user.UserNotContentCreatorException;
import com.ajaksmaniac.streamify.exception.user.UserNotExistentException;
import com.ajaksmaniac.streamify.exception.video.VideoAlreadyExistsException;
import com.ajaksmaniac.streamify.exception.video.VideoNotFoundException;
import com.ajaksmaniac.streamify.exception.video.UserNotPermittedToDeleteVideoException;
import com.ajaksmaniac.streamify.exception.video.UserNotPermittedToUpdateVideoException;
import com.ajaksmaniac.streamify.exception.video.UserNotPermittedToUploadVideoException;
import com.ajaksmaniac.streamify.mapper.VideoDetailsMapper;
import com.ajaksmaniac.streamify.repository.ChannelRepository;
import com.ajaksmaniac.streamify.repository.UserRepository;
import com.ajaksmaniac.streamify.repository.VideoRepository;
import com.ajaksmaniac.streamify.util.VideoUtilService;
import com.ajaksmaniac.streamify.util.UserUtil;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private VideoDetailsMapper mapper;

    @Autowired
    UserUtil userUtil;

    public Resource getVideo(Long id) throws IOException {

        if (!videoRepository.existsById(id)) {
            throw new VideoNotFoundException(id);
        }
        return new VideoUtilService().getFileAsResource(id.toString());

    }

    public VideoDetailsDto getVideoDetails(Long id) {

        if (!videoRepository.existsById(id)) {
            throw new VideoNotFoundException(id);
        }

        VideoDetailsEntity en = videoRepository.findById(id).get();

        return mapper.convertToDto(en);
    }

    public List<VideoDetailsDto> getAllVideosDetails() {

        return mapper.convertListToDTO(videoRepository.getAllVideos());
    }

    public VideoDetailsDto saveVideo(MultipartFile file, String name, Long channelId, String description,Long authenticatedUserId) throws IOException {
        if (!channelRepository.existsById(channelId)) {
            throw new ChannelNotFoundException(channelId);
        }

        if (!userUtil.isUserAdmin(sessionUser(authenticatedUserId))) {
            if (!userUtil.isUserContentCreator(sessionUser(authenticatedUserId)))
                throw new UserNotContentCreatorException(sessionUser(authenticatedUserId).getId());

            if (!channelRepository.isChannelOwnedByUser(channelId,sessionUser(authenticatedUserId))) throw new UserNotPermittedToUploadVideoException(sessionUser(authenticatedUserId).getId());
        }

        if (videoRepository.existsByName(name)) {
            throw new VideoAlreadyExistsException(name);
        }


        LocalDateTime now = LocalDateTime.now();
        LocalDate date = now.toLocalDate();
        VideoDetailsEntity newVideoDetailsEntity = new VideoDetailsEntity(name, new ChannelEntity(channelId), java.sql.Date.valueOf(date), description);

        VideoDetailsEntity saved = videoRepository.save(newVideoDetailsEntity);
        VideoUtilService.saveFile(saved.getId().toString(), name, file);

        return mapper.convertToDto(saved);
    }

    public void deleteVideo(Long id,Long authenticatedUserId) throws IOException {

        if (!videoRepository.existsById(id)) {
            throw new VideoNotFoundException(id);
        }

        if (!userUtil.isUserAdmin(sessionUser(authenticatedUserId))) {
            if (!userUtil.isUserContentCreator(sessionUser(authenticatedUserId)))
                throw new UserNotContentCreatorException(sessionUser(authenticatedUserId).getId());

            if (!videoRepository.isVideoOwnedByUser(id,sessionUser(authenticatedUserId).getId())) throw new UserNotPermittedToDeleteVideoException(sessionUser(authenticatedUserId).getId());
        }

        new VideoUtilService().deleteFile(id.toString());

        videoRepository.deleteById(id);

    }

    public VideoDetailsDto updateVideo(Long id, String name, String description, MultipartFile file, Long authenticatedUserId) throws IOException {
        Optional<VideoDetailsEntity> entity = videoRepository.findById(id);
        if (entity.isEmpty()) {
            throw new VideoNotFoundException(id);
        }

        if (!userUtil.isUserAdmin(sessionUser(authenticatedUserId))) {
            if (!userUtil.isUserContentCreator(sessionUser(authenticatedUserId)))
                throw new UserNotContentCreatorException(sessionUser(authenticatedUserId).getId());
            if (!videoRepository.isVideoOwnedByUser(id,sessionUser(authenticatedUserId).getId())) throw new UserNotPermittedToUpdateVideoException(sessionUser(authenticatedUserId).getId());
        }


        if(!name.equals(entity.get().getName())){
            if (videoRepository.existsByName(name)) {
                throw new VideoAlreadyExistsException(name);
            }
        }
            entity.get().setName(name);

            entity.get().setDescription(description);
            videoRepository.save(entity.get());

        if(file != null){
            VideoUtilService.updateVideo(id.toString(), name,file);

        }else{
            VideoUtilService.updateVideo(id.toString(), name);

        }
        return mapper.convertToDto(entity.get());
    }

    public List<VideoDetailsDto> search(String keywords) {
//        log.info(videoRepository.search(keywords).toString());
         Map videoMap = new HashMap<Long, VideoDetailsEntity>();
        List<String> keywordsList = Arrays.stream(keywords.split(" ")).toList();
        keywordsList.forEach(kw ->{
            videoRepository.search(kw).forEach(v->{
                if(!videoMap.containsKey(v.getId())){
                    videoMap.put(v.getId(),v);
                }
            });
        });

        return mapper.convertListToDTO(videoMap.values().stream().toList());
    }

    public List<VideoDetailsDto> getAllVideosByChannel(Long id) {
        return mapper.convertListToDTO(videoRepository.getVideosForChannel(id));
    }

    private UserEntity sessionUser(Long userId) {
        Optional< UserEntity > user = Optional.ofNullable(userRepository.findById(userId).orElseThrow(() ->
                new UserNotExistentException(String.valueOf(userId))));
        return user.get();
    }
}

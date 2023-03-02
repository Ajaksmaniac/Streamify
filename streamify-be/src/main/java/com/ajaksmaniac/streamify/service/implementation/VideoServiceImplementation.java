package com.ajaksmaniac.streamify.service.implementation;

import com.ajaksmaniac.streamify.dto.VideoDetailsDto;
import com.ajaksmaniac.streamify.entity.ChannelEntity;
import com.ajaksmaniac.streamify.entity.UserEntity;
import com.ajaksmaniac.streamify.entity.VideoDetailsEntity;
import com.ajaksmaniac.streamify.exception.channel.ChannelNotFoundException;
import com.ajaksmaniac.streamify.exception.user.UserNotContentCreatorException;
import com.ajaksmaniac.streamify.exception.video.VideoAlreadyExistsException;
import com.ajaksmaniac.streamify.exception.video.VideoNotFoundException;
import com.ajaksmaniac.streamify.exception.video.UserNotPermittedToDeleteVideoException;
import com.ajaksmaniac.streamify.exception.video.UserNotPermittedToUpdateVideoException;
import com.ajaksmaniac.streamify.exception.video.UserNotPermittedToUploadVideoException;
import com.ajaksmaniac.streamify.mapper.VideoDetailsMapper;
import com.ajaksmaniac.streamify.repository.ChannelRepository;
import com.ajaksmaniac.streamify.repository.VideoRepository;
import com.ajaksmaniac.streamify.service.VideoService;
import com.ajaksmaniac.streamify.util.VideoUtilService;
import com.ajaksmaniac.streamify.util.UserUtil;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@AllArgsConstructor
@Slf4j
public class VideoServiceImplementation implements VideoService {

    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private VideoDetailsMapper mapper;

    @Autowired
    UserUtil userUtil;


    @Override
    public Resource getVideo(Long id) throws IOException {

        if (!videoRepository.existsById(id)) {
            throw new VideoNotFoundException();
        }
        return new VideoUtilService().getFileAsResource(id.toString());

    }

    @Override
    public VideoDetailsDto getVideoDetails(Long id) {

        if (!videoRepository.existsById(id)) {
            throw new VideoNotFoundException();
        }

        VideoDetailsEntity en = videoRepository.findById(id).get();

        return mapper.convertToDto(en);
    }

    @Override
    public List<VideoDetailsDto> getAllVideosDetails() {

        return mapper.convertListToDTO(videoRepository.getAllVideos());
    }

    @Override
    public VideoDetailsDto saveVideo(MultipartFile file, String name, Long channelId, String description) throws IOException {
        if (!channelRepository.existsById(channelId)) {
            throw new ChannelNotFoundException();
        }

        if (!userUtil.isUserAdmin(sessionUser())) {
            if (!userUtil.isUserContentCreator(sessionUser()))
                throw new UserNotContentCreatorException();

            if (!channelRepository.isChannelOwnedByUser(channelId,sessionUser())) throw new UserNotPermittedToUploadVideoException();
        }

        if (videoRepository.existsByName(name)) {
            throw new VideoAlreadyExistsException();
        }

        ChannelEntity channel = channelRepository.findById(channelId).get();

        LocalDateTime now = LocalDateTime.now();
        LocalDate date = now.toLocalDate();
        VideoDetailsEntity newVideoDetailsEntity = new VideoDetailsEntity(name, channel, java.sql.Date.valueOf(date), description);
        VideoDetailsEntity saved = videoRepository.save(newVideoDetailsEntity);
        saved.setVideoUrl("/video/id/" + saved.getId());
        saved.setDescription(description);
        saved.setPostedAt(java.sql.Date.valueOf(date));

        VideoUtilService.saveFile(saved.getId().toString(), name, file);

        return mapper.convertToDto(videoRepository.save(saved));
    }

    @Override
    public void deleteVideo(Long id) throws IOException {
        if (!videoRepository.existsById(id)) {
            throw new VideoNotFoundException();
        }

        if (!userUtil.isUserAdmin(sessionUser())) {
            if (!userUtil.isUserContentCreator(sessionUser()))
                throw new UserNotContentCreatorException();

            if (!videoRepository.isVideoOwnedByUser(id,sessionUser().getId())) throw new UserNotPermittedToDeleteVideoException();
        }

        new VideoUtilService().deleteFile(id.toString());

        videoRepository.deleteById(id);

    }

    @Override
    public VideoDetailsDto updateVideo(Long id, String name, String description, MultipartFile file) throws IOException {

        if (!videoRepository.existsById(id)) {
            throw new VideoNotFoundException();
        }

        if (!userUtil.isUserAdmin(sessionUser())) {
            if (!userUtil.isUserContentCreator(sessionUser()))
                throw new UserNotContentCreatorException();
            if (!videoRepository.isVideoOwnedByUser(id,sessionUser().getId())) throw new UserNotPermittedToUpdateVideoException();
        }


        VideoDetailsEntity entity = videoRepository.findById(id).get();

        if(!name.equals(entity.getName())){
            if (videoRepository.existsByName(name)) {
                throw new VideoAlreadyExistsException();
            }
            entity.setName(name);
            videoRepository.save(entity);
        }
        if(!description.equals(entity.getDescription())){

            entity.setDescription(description);
            videoRepository.save(entity);

        }


        if(file != null){
            VideoUtilService.updateVideo(id.toString(), name,file);

        }else{
            VideoUtilService.updateVideo(id.toString(), name);

        }
        return mapper.convertToDto(entity);
    }

    @Override
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

    private UserEntity sessionUser() {
        return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

package com.ajaksmaniac.streamify.service.implementation;

import com.ajaksmaniac.streamify.dto.VideoDetailsDto;
import com.ajaksmaniac.streamify.entity.ChannelEntity;
import com.ajaksmaniac.streamify.entity.VideoDetailsEntity;
import com.ajaksmaniac.streamify.exception.UserNotExistantException;
import com.ajaksmaniac.streamify.exception.VideoAlreadyExistsException;
import com.ajaksmaniac.streamify.exception.VideoNotFoundException;
import com.ajaksmaniac.streamify.repository.ChannelRepository;
import com.ajaksmaniac.streamify.repository.UserRepository;
import com.ajaksmaniac.streamify.repository.VideoRepository;
import com.ajaksmaniac.streamify.service.VideoService;
import com.ajaksmaniac.streamify.util.FileUtil;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class VideoServiceImplementation implements VideoService {

	@Autowired
    private VideoRepository videoRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChannelRepository channelRepository;


    @Override
    public Resource getVideo(Long id) throws IOException {

        if(!videoRepository.existsById(id)){
            throw new VideoNotFoundException();
        }
        return  new FileUtil().getFileAsResource(id.toString());

    }

    @Override
    public VideoDetailsDto getVideoDetails(Long id) {

        if(!videoRepository.existsById(id)){
            throw new VideoNotFoundException();
        }

        VideoDetailsEntity videoDetailsEntity = videoRepository.findById(id).get();

        VideoDetailsDto dto = new VideoDetailsDto(videoDetailsEntity.getId(), videoDetailsEntity.getName(), videoDetailsEntity.getChannel().getChannelName(), "/video/id/"+ videoDetailsEntity.getId());
        return dto;
    }

    @Override
    public List<VideoDetailsDto> getAllVideosDetails() {

        List<VideoDetailsDto> list = new ArrayList<>();

        videoRepository.getAllVideos().stream().forEach(v->{
            list.add(new VideoDetailsDto(v.getId(), v.getName(),v.getChannel().getChannelName(), "/video/id/"+v.getId()));
        });

        return list;
    }

    @Override
    public void saveVideo(MultipartFile file, String name, String channelName) throws IOException {
        if(videoRepository.existsByName(name)){
            throw new VideoAlreadyExistsException();
        }

        if(!channelRepository.existsByChannelName(channelName)){
            throw new UserNotExistantException();
        }

        ChannelEntity channel = channelRepository.findByChannelName(channelName.toLowerCase());

        if(!userRepository.existsByUsername(channel.getUser().getUsername())){
            throw new UserNotExistantException();
        }

        VideoDetailsEntity newVideoDetailsEntity = new VideoDetailsEntity(name, channel);
        VideoDetailsEntity saved = videoRepository.save(newVideoDetailsEntity);
        FileUtil.saveFile(saved.getId().toString(), name,file);
    }

    @Override
    public List<String> getAllVideoNames() {
        return videoRepository.getAllEntryNames();
    }
}

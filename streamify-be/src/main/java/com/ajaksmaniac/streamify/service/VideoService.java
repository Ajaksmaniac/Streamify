package com.ajaksmaniac.streamify.service;

import com.ajaksmaniac.streamify.dto.VideoDetailsDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VideoService {



    Resource getVideo(Long id) throws IOException ;

    VideoDetailsDto getVideoDetails(Long id);

    List<VideoDetailsDto> getAllVideosDetails();


    void saveVideo(MultipartFile file, String name, String channelName, String description) throws IOException;
    List<String> getAllVideoNames();

    void deleteVideo(Long id) throws IOException;

    void updateVideo(Long id, String name, String description);
}

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

    VideoDetailsDto saveVideo(MultipartFile file, String name, Long channelId, String description, Long authenticatedUserId) throws IOException;

    void deleteVideo(Long id,Long authenticatedUserId) throws IOException;

    VideoDetailsDto updateVideo(Long id, String name, String description, MultipartFile file,Long authenticatedUserId) throws IOException;

    List<VideoDetailsDto> search(String keywords);
    List<VideoDetailsDto> getAllVideosByChannel(Long id);
}

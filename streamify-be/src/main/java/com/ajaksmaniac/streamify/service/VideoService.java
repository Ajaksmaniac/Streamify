package com.ajaksmaniac.streamify.service;

import com.ajaksmaniac.streamify.entity.VideoEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VideoService {

    VideoEntity getVideo(String name);
    void saveVideo(MultipartFile file, String name, String username) throws IOException;
    List<String> getAllVideoNames();
}

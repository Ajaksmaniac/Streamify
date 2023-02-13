package com.ajaksmaniac.streamify.service.implementation;

import com.ajaksmaniac.streamify.entity.UserEntity;
import com.ajaksmaniac.streamify.entity.VideoEntity;
import com.ajaksmaniac.streamify.exception.UserNotExistantException;
import com.ajaksmaniac.streamify.exception.VideoAlreadyExistsException;
import com.ajaksmaniac.streamify.exception.VideoNotFoundException;
import com.ajaksmaniac.streamify.repository.UserRepository;
import com.ajaksmaniac.streamify.repository.VideoRepository;
import com.ajaksmaniac.streamify.service.VideoService;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class VideoServiceImplementation implements VideoService {

	@Autowired
    private VideoRepository videoRepository;
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public VideoEntity getVideo(String name) {
        if(!videoRepository.existsByName(name)){
            throw new VideoNotFoundException();
        }
        return videoRepository.findByName(name);
    }

    @Override
    public void saveVideo(MultipartFile file, String name, String username) throws IOException {
        if(videoRepository.existsByName(name)){
            throw new VideoAlreadyExistsException();
        }

        if(!userRepository.existsByUsername(username)){
            throw new UserNotExistantException();
        }

        UserEntity user = userRepository.findByUsername(username);

        VideoEntity newVideoEntity = new VideoEntity(name,file.getBytes(), user);
        videoRepository.save(newVideoEntity);
    }

    @Override
    public List<String> getAllVideoNames() {
        return videoRepository.getAllEntryNames();
    }
}

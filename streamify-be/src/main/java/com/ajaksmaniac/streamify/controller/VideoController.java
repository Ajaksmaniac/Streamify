package com.ajaksmaniac.streamify.controller;

import com.ajaksmaniac.streamify.service.VideoService;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("video")
@AllArgsConstructor
@CrossOrigin
public class VideoController {
	@Autowired
	@Qualifier(value = "videoServiceImplementation")
    private VideoService videoService;

    @PostMapping()
    public ResponseEntity<String> saveVideo(@RequestParam("file") MultipartFile file, @RequestParam("name") String name, @RequestParam("username") String username) throws IOException {

            videoService.saveVideo(file, name, username);

        return ResponseEntity.ok("Video saved successfully.");
    }

    @GetMapping("{name}")
    public ResponseEntity<Resource> getVideoByName(@PathVariable("name") String name){

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new ByteArrayResource(videoService.getVideo(name).getData()));

    }

    @GetMapping("all")
//    @CrossOrigin(value = "http://localhost:8080")
    public ResponseEntity<List<String>> getAllVideoNames(){
        return ResponseEntity.ok(videoService.getAllVideoNames());
    }
}

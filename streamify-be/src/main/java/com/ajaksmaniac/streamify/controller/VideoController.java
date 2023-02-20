package com.ajaksmaniac.streamify.controller;

import com.ajaksmaniac.streamify.dto.VideoDetailsDto;
import com.ajaksmaniac.streamify.service.VideoService;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
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

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getVideoById(@PathVariable("id") Long id){

        Resource resource = null;

        try{
            resource = videoService.getVideo(id);
        } catch (IOException e) {
//            throw ResponseEntity.internalServerError().build();
        }

        if(resource == null){
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }

        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);

    }

    @GetMapping("/details/id/{id}")
    public ResponseEntity<VideoDetailsDto> getVideoDetailsById(@PathVariable("id") Long id){
        return ResponseEntity.ok(videoService.getVideoDetails(id));


    }

    @GetMapping("/details")
    public ResponseEntity<List<VideoDetailsDto>> getAllVideoDetails(){

        return ResponseEntity.ok(videoService.getAllVideosDetails());


    }
    @GetMapping("all")
//    @CrossOrigin(value = "http://localhost:8080")
    public ResponseEntity<List<String>> getAllVideoNames(){
        return ResponseEntity.ok(videoService.getAllVideoNames());
    }
}

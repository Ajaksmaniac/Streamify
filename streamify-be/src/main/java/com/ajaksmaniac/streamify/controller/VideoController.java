package com.ajaksmaniac.streamify.controller;

import com.ajaksmaniac.streamify.dto.VideoDetailsDto;
import com.ajaksmaniac.streamify.service.ChannelService;
import com.ajaksmaniac.streamify.service.VideoService;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public ResponseEntity<VideoDetailsDto> saveVideo(@RequestParam("file") MultipartFile file, @RequestParam("name") String name, @RequestParam("channelId") Long channelId,  @RequestParam("description") String description) throws IOException {


        return ResponseEntity.ok(videoService.saveVideo(file, name, channelId,description));


    }

    @PutMapping("/id/{id}")
    public ResponseEntity<VideoDetailsDto> update(@PathVariable("id") Long id,@RequestParam("name") String name,  @RequestParam("description") String description,@RequestParam(value = "file",required = false) MultipartFile file) throws IOException {


        return ResponseEntity.ok( videoService.updateVideo(id, name,description,file));


    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getVideoById(@PathVariable("id") Long id) throws IOException {
        Resource  resource = videoService.getVideo(id);
        try{
            String contentType = "application/octet-stream";
            String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                    .body(resource);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/details/id/{id}")
    public ResponseEntity<VideoDetailsDto> getVideoDetailsById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(videoService.getVideoDetails(id));
    }
    @GetMapping("/channel/id/{id}")
    public ResponseEntity<List<VideoDetailsDto>> getVideoDetailsByChannelId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(videoService.getAllVideosByChannel(id));
    }

    @GetMapping("/details")
    public ResponseEntity<List<VideoDetailsDto>> getAllVideoDetails() {

        return ResponseEntity.ok(videoService.getAllVideosDetails());


    }

    @GetMapping("/search")
    public ResponseEntity<List<VideoDetailsDto>> search(@RequestParam("keywords") String keywords) {
        return ResponseEntity.ok(videoService.search(keywords));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteVideo(@PathVariable("id") Long id) throws IOException {
        videoService.deleteVideo(id);
        return ResponseEntity.ok(String.format("Video with id %d successfully deleted",id));
    }
}

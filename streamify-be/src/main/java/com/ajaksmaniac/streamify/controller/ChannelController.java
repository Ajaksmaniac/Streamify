package com.ajaksmaniac.streamify.controller;


import com.ajaksmaniac.streamify.dto.ChannelDto;
import com.ajaksmaniac.streamify.dto.VideoDetailsDto;
import com.ajaksmaniac.streamify.exception.UserNotExistantException;
import com.ajaksmaniac.streamify.exception.VideoNotFoundException;
import com.ajaksmaniac.streamify.service.ChannelService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/channel")
public class ChannelController {

    @Autowired
    @Qualifier(value = "channelServiceImplementation")
    ChannelService channelService;

    @GetMapping("/id/{id}")
    public ResponseEntity<ChannelDto> getChannelDetailsById(@PathVariable("id") Long id){
        return ResponseEntity.ok(channelService.getChannelById(id));

    }
    @PostMapping("/channel")
    public ResponseEntity<String> saveChannel(@RequestBody ChannelDto dto){
        try {
            channelService.createChannel(dto);
            return ResponseEntity.ok("Channel saved");

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @DeleteMapping("/channel/{id}")
    public ResponseEntity<String> deleteChannelById(@PathVariable("id") Long id){
        try {
            channelService.deleteById(id);
            return ResponseEntity.ok("Channel Deleted");

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @GetMapping("/all")
    public ResponseEntity getAllChannels(){
        try {

            return ResponseEntity.status(HttpStatus.OK).body( channelService.getAllChannels());

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

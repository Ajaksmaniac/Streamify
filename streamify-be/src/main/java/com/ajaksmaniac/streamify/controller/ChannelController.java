package com.ajaksmaniac.streamify.controller;


import com.ajaksmaniac.streamify.dto.ChannelDto;
import com.ajaksmaniac.streamify.dto.VideoDetailsDto;
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
@CrossOrigin
public class ChannelController {

    @Autowired
    @Qualifier(value = "channelServiceImplementation")
    ChannelService channelService;

    @GetMapping("/id/{id}")
    public ResponseEntity<ChannelDto> getChannelDetailsById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(channelService.getChannelById(id));

    }
    @GetMapping("/user/id/{id}")
    public ResponseEntity<List<ChannelDto>> getChannelDetailsByUserId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(channelService.getChannelByUserId(id));

    }

    @PostMapping
    public ResponseEntity<ChannelDto> saveChannel(@RequestBody ChannelDto dto) {


        return ResponseEntity.ok(channelService.createChannel(dto));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteChannelById(@PathVariable("id") Long id) {
            channelService.deleteById(id);
            return ResponseEntity.ok(String.format("Channel with id %d successfully deleted", id));

    }

    @PutMapping
    public  ResponseEntity<ChannelDto> updateChannel(@RequestBody ChannelDto dto){

        return ResponseEntity.ok(channelService.updateChannel(dto));

    }


    @GetMapping()
    public ResponseEntity getAllChannels() {
        try {

            return ResponseEntity.status(HttpStatus.OK).body(channelService.getAllChannels());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<ChannelDto>> search(@RequestParam("keywords") String keywords) {
        return ResponseEntity.ok(channelService.search(keywords));
    }
}

package com.ajaksmaniac.streamify.controller;


import com.ajaksmaniac.streamify.dto.ChannelDto;
import com.ajaksmaniac.streamify.service.ChannelService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/channel")
public class ChannelController {

    @Autowired
    @Qualifier(value = "channelServiceImplementation")
    ChannelService channelService;

    @GetMapping("/id/{id}")
    public ResponseEntity<ChannelDto> getChannelDetailsById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(channelService.getChannelById(id));

    }

    @PostMapping
    public ResponseEntity<ChannelDto> saveChannel(@RequestBody ChannelDto dto) {


        return ResponseEntity.ok(channelService.createChannel(dto));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteChannelById(@PathVariable("id") Long id) {
            channelService.deleteById(id);
            return ResponseEntity.ok("Channel Deleted");

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
}

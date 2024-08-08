package com.ajaksmaniac.streamify.controller;


import com.ajaksmaniac.streamify.dto.ChannelDto;
import com.ajaksmaniac.streamify.service.implementation.ChannelService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ChannelService channelService;

    @GetMapping("/id/{id}")
    public ResponseEntity<ChannelDto> getChannelDetailsById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(channelService.getChannelById(id));

    }
    @GetMapping("/user/id/{id}")
    public ResponseEntity<List<ChannelDto>> getChannelDetailsByUserId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(channelService.getChannelByUserId(id));

    }

    @PostMapping
    public ResponseEntity<ChannelDto> saveChannel(@RequestBody ChannelDto dto, @RequestHeader("x-auth-user-id") Long authenticatedUser) {


        return ResponseEntity.ok(channelService.createChannel(dto,authenticatedUser));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteChannelById(@PathVariable("id") Long id, @RequestHeader("x-auth-user-id") Long authenticatedUser) {
            channelService.deleteById(id,authenticatedUser);
            return ResponseEntity.ok(String.format("Channel with id %d successfully deleted", id));

    }

    @PutMapping
    public  ResponseEntity<ChannelDto> updateChannel(@RequestBody ChannelDto dto, @RequestHeader("x-auth-user-id") Long authenticatedUser){

        return ResponseEntity.ok(channelService.updateChannel(dto,authenticatedUser));

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

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribe(@RequestHeader("channel") Long channel, @RequestHeader("x-auth-user-id") Long authenticatedUser) {
        channelService.subscribeToChannel(authenticatedUser,channel);
        return ResponseEntity.ok("Subscribed");
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<String> unsubscribe(@RequestHeader("channel") Long channel, @RequestHeader("x-auth-user-id") Long authenticatedUser) {
        channelService.unsubscribeFromChannel(authenticatedUser,channel);
        return ResponseEntity.ok("Subscribed");
    }
}

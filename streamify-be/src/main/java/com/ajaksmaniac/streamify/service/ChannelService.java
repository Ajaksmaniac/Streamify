package com.ajaksmaniac.streamify.service;

import com.ajaksmaniac.streamify.dto.ChannelDto;
import com.ajaksmaniac.streamify.dto.VideoDetailsDto;

import java.util.List;

public interface ChannelService {

    ChannelDto getChannelById(Long channelId);
    ChannelDto createChannel(ChannelDto channelDto);
    ChannelDto updateChannel(ChannelDto channelDto);
    void deleteById(Long id);
    List<ChannelDto> getAllChannels();
    List<ChannelDto> search(String keywords);

}

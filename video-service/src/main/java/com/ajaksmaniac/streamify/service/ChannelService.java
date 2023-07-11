package com.ajaksmaniac.streamify.service;

import com.ajaksmaniac.streamify.dto.ChannelDto;
import com.ajaksmaniac.streamify.dto.VideoDetailsDto;

import java.util.List;

public interface ChannelService {

    ChannelDto getChannelById(Long channelId);
    List<ChannelDto> getChannelByUserId(Long userId);
    ChannelDto createChannel(ChannelDto channelDto,Long authenticatedUserId);
    ChannelDto updateChannel(ChannelDto channelDto,Long authenticatedUserId);
    void deleteById(Long id,Long authenticatedUserId);
    List<ChannelDto> getAllChannels();
    List<ChannelDto> search(String keywords);

}

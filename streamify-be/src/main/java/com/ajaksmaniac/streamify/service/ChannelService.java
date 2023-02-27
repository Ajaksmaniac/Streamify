package com.ajaksmaniac.streamify.service;

import com.ajaksmaniac.streamify.dto.ChannelDto;
import com.ajaksmaniac.streamify.dto.RoleDto;

import java.util.List;

public interface ChannelService {

    ChannelDto getChannelById(Long channelId);
    void createChannel(ChannelDto channelDto);
    void updateChannel(ChannelDto channelDto);
    void deleteById(Long id);
    List<ChannelDto> getAllChannels();
}

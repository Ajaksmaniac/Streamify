package com.ajaksmaniac.streamify.service;

import com.ajaksmaniac.streamify.dto.ChannelDto;
import com.ajaksmaniac.streamify.dto.ChannelOnlyDto;
import com.ajaksmaniac.streamify.dto.RoleDto;

import java.util.List;

public interface ChannelService {

    ChannelDto getChannelById(Long channelId);
    void createChannel(ChannelOnlyDto channelDto);
    void updateChannel(ChannelOnlyDto channelDto);
    void deleteById(Long id);
    List<ChannelOnlyDto> getAllChannels();
}

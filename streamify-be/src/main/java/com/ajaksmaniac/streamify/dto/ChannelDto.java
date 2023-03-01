package com.ajaksmaniac.streamify.dto;


import com.ajaksmaniac.streamify.entity.UserEntity;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonSerialize
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChannelDto {

    private Long id;

    private String channelName;

    private String username;

    private List<VideoDetailsDto> videos;

    public ChannelDto(Long id, String channelName, String username) {
        this.id = id;
        this.channelName = channelName;
        this.username = username;
    }
}

package com.ajaksmaniac.streamify.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonSerialize
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChannelOnlyDto {

    private Long id;

    private String channelName;

    private String username;
}

package com.ajaksmaniac.identityservice.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonSerialize
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChannelDto {
    private Long id;
    private String channelName;

}
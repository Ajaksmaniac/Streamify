package com.ajaksmaniac.streamify.notifications.dto;

import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@JsonSerializableSchema
public class UserDto {

    Long id;
    String username;

    List<ChannelDto> subscribedChannels;
}

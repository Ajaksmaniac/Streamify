package com.ajaksmaniac.streamify.dto;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonSerialize
@Data
@NoArgsConstructor
public class VideoDetailsDto {
    private Long id;
    private String name;

//    private String postedBy;
    private Long channelId;

    private String url;
    private String description;

    public VideoDetailsDto(Long id, String name, Long channelId, String description) {
        this.id = id;
        this.name = name;
        this.channelId = channelId;
        this.description = description;
    }
}

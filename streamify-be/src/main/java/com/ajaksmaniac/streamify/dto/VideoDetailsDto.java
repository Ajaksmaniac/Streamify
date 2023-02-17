package com.ajaksmaniac.streamify.dto;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;

@JsonSerialize
@Data
@AllArgsConstructor
public class VideoDetailsDto {
    private Long id;
    private String name;

    private String postedBy;

    private String url;
}

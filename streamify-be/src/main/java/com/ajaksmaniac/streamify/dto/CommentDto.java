package com.ajaksmaniac.streamify.dto;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonSerialize
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;

    private String content;
    private Long videoId;
    private String username;

    public CommentDto(String content,Long videoId,String username){
        this.content =content;
        this.videoId = videoId;
        this.username = username;
    }

}

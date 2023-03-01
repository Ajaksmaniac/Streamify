package com.ajaksmaniac.streamify.dto;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@JsonSerialize
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;

    private String content;
    private Long videoId;
    private String username;

    private Date commented_at;

    public CommentDto(String content,Long videoId,String username,Date commented_at){
        this.content =content;
        this.videoId = videoId;
        this.username = username;
        this.commented_at = commented_at;
    }

}

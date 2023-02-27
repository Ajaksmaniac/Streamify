package com.ajaksmaniac.streamify.entity;

import com.ajaksmaniac.streamify.dto.VideoDetailsDto;
import jakarta.persistence.*;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.sql.Date;

@Table(name = "video_details")
@Data
@NoArgsConstructor
@Entity
public class VideoDetailsEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "video_id")
    private Long id;

    @Column(unique = true)
    private String name;

    private String description;

    private Date postedAt;

    private String videoUrl;
    
    @ManyToOne
    @JoinColumn(name = "channel_id", nullable = false)
    private ChannelEntity channel;

    public VideoDetailsEntity(String name, ChannelEntity channel) {
        this.name = name;
        this.channel = channel;
    }

}

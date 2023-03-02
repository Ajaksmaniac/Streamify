package com.ajaksmaniac.streamify.entity;

import com.ajaksmaniac.streamify.dto.VideoDetailsDto;
import jakarta.persistence.*;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "videoDetails",orphanRemoval = true)
    private List<CommentEntity> comments;
    @ManyToOne
    @JoinColumn(name = "channel_id", nullable = false)
    private ChannelEntity channel;
    public VideoDetailsEntity(String name, ChannelEntity channel, Date postedAt, String description) {
        this.name = name;
        this.channel = channel;
    }

    public VideoDetailsEntity(Long id,String name, ChannelEntity channel, String description,Date postedAt,String videoUrl) {
        this.id = id;
        this.channel = channel;
        this.name = name;
        this.postedAt = postedAt;
        this.description = description;
        this.videoUrl = videoUrl;

    }

}

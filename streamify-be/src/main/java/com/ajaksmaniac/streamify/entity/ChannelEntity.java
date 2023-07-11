package com.ajaksmaniac.streamify.entity;


import com.ajaksmaniac.streamify.dto.ChannelDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;

@AllArgsConstructor
@Data
@Table(name = "channel")
@Entity
@NoArgsConstructor
public class ChannelEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "channel_id")
    private Long id;

    @Column(name = "channel_name", unique = true, nullable = false)
    private String channelName;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToMany
    private List<UserEntity> subscribers;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "channel", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<VideoDetailsEntity> videos;

    public ChannelEntity(Long id){
        this.id = id;
    }


}

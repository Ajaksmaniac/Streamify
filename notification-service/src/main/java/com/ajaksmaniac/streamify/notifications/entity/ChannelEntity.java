package com.ajaksmaniac.streamify.notifications.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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

    @ManyToMany(mappedBy = "subscribedChannels", fetch = FetchType.EAGER)
    private List<UserEntity> subscribers;

    public ChannelEntity(Long id){
        this.id = id;
    }


}

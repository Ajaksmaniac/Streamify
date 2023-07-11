package com.ajaksmaniac.identityservice.entity;

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



    public ChannelEntity(Long id){
        this.id = id;
    }


}
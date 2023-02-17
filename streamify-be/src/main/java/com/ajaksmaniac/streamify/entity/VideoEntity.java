package com.ajaksmaniac.streamify.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;

import java.beans.ConstructorProperties;

//@Data

@Table(name = "video")
@Data
@NoArgsConstructor
@Entity
public class VideoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)


    private String name;

//    @Lob
//    private byte[] data;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)

    private UserEntity user;

    public VideoEntity(String name, UserEntity user) {
        this.name = name;
//        this.data = data;
        this.user = user;
    }





}

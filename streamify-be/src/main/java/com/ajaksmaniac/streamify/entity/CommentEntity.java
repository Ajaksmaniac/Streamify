package com.ajaksmaniac.streamify.entity;


import jakarta.persistence.*;

import lombok.*;

@RequiredArgsConstructor
@Data
@Table(name = "comment")
@Entity
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;

    @ManyToOne()
//    @JoinColumn(name = "video_id", nullable = false)
    private VideoEntity video;
    @ManyToOne()
//    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

}

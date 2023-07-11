package com.ajaksmaniac.streamify.entity;


import jakarta.persistence.*;

import lombok.*;

import java.sql.Date;

@RequiredArgsConstructor
@Data
@Table(name = "comment")
@Entity
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private String content;

    @ManyToOne()
    @JoinColumn(name = "video_id", nullable = false)
    private VideoDetailsEntity videoDetails;
    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "commentedAt", nullable = false)
    private Date commentedAt;

}

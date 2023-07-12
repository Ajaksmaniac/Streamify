package com.ajaksmaniac.streamify.notifications.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.sql.Date;

@Data
@RequiredArgsConstructor
@Table(name = "notification")
@Entity
@AllArgsConstructor
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "notification_id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "content")
    private String content;


    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "url")
    private String url;

    public NotificationEntity(Long userId, String content, Date createdAt, String url) {
        this.userId = userId;
        this.content = content;
        this.createdAt = createdAt;
        this.url = url;
    }
}

package com.ajaksmaniac.streamify.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Type;

@Data
@RequiredArgsConstructor
@Table(name = "notification")
@Entity
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "notification_id")
    private Long id;

    private String readed;

    private String message;
}

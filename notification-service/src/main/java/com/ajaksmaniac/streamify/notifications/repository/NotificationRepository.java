package com.ajaksmaniac.streamify.notifications.repository;

import com.ajaksmaniac.streamify.notifications.entity.ChannelEntity;
import com.ajaksmaniac.streamify.notifications.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

//    @Query(value = "SELECT n FROM NotificationEntity n WHERE n.userId = :userId")
//    List<NotificationEntity> findByUserId(Long userId);

    @Query(value = "SELECT * FROM notification n WHERE n.user_id = :userId", nativeQuery = true)
    List<NotificationEntity> findByUserId(@Param("userId")Long userId);
}

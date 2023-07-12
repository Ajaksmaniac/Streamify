package com.ajaksmaniac.streamify.notifications.service;

import com.ajaksmaniac.streamify.notifications.dto.NotificationDto;
import com.ajaksmaniac.streamify.notifications.entity.NotificationEntity;

import java.util.List;

public interface NotificationService {
    NotificationDto save(String content, Long userId, String videoUrl);
    List<NotificationDto> getUserNotifications(Long userId);

    void delete(Long id);
}

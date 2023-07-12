package com.ajaksmaniac.streamify.notifications.service.implementation;

import com.ajaksmaniac.streamify.notifications.dto.NotificationDto;
import com.ajaksmaniac.streamify.notifications.entity.NotificationEntity;
import com.ajaksmaniac.streamify.notifications.mapper.NotificationMapper;
import com.ajaksmaniac.streamify.notifications.repository.NotificationRepository;
import com.ajaksmaniac.streamify.notifications.service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class NotificationServiceImplementation implements NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    NotificationMapper mapper;

    @Override
    public NotificationDto save(String content, Long userId, String videoUrl) {
        LocalDateTime now = LocalDateTime.now();
        LocalDate date = now.toLocalDate();
        NotificationEntity entity  = new NotificationEntity(userId,content,java.sql.Date.valueOf(date), videoUrl);
        NotificationEntity ent = notificationRepository.save(entity);
        return mapper.convertToDto(ent);
    }

    @Override
    public List<NotificationDto> getUserNotifications(Long userId) {

        return  notificationRepository.findByUserId(userId).stream().map(notificationEntity -> mapper.convertToDto(notificationEntity)).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        notificationRepository.deleteById(id);
    }
}

package com.ajaksmaniac.streamify.notifications.mapper;

import com.ajaksmaniac.streamify.notifications.dto.NotificationDto;
import com.ajaksmaniac.streamify.notifications.entity.NotificationEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class NotificationMapper {

    ModelMapper mapper = new ModelMapper();

    public NotificationDto convertToDto(NotificationEntity entity) {
        NotificationDto dto =mapper.map(entity, NotificationDto.class);
        dto.setCreated_at(Date.valueOf(entity.getCreatedAt().toLocalDate()));

        return dto;
    }



}

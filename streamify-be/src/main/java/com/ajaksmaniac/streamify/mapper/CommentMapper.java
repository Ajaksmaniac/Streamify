package com.ajaksmaniac.streamify.mapper;


import com.ajaksmaniac.streamify.dto.CommentDto;
import com.ajaksmaniac.streamify.dto.RoleDto;
import com.ajaksmaniac.streamify.entity.CommentEntity;
import com.ajaksmaniac.streamify.entity.RoleEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class CommentMapper {

    ModelMapper mapper = new ModelMapper();


    public CommentDto convertToDto(CommentEntity entity) {

        CommentDto dto = mapper.map(entity, CommentDto.class);
        dto.setUserId(entity.getUser().getId());
        dto.setCommented_at(Date.valueOf(entity.getCommentedAt().toLocalDate()));
        return dto;
    }

    public CommentEntity convertToEntity(CommentDto dto) {
        return mapper.map(dto, CommentEntity.class);
    }
}

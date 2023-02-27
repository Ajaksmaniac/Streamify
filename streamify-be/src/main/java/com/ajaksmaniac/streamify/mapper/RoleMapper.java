package com.ajaksmaniac.streamify.mapper;

import com.ajaksmaniac.streamify.dto.RoleDto;
import com.ajaksmaniac.streamify.dto.VideoDetailsDto;
import com.ajaksmaniac.streamify.entity.RoleEntity;
import com.ajaksmaniac.streamify.entity.VideoDetailsEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {
    ModelMapper mapper = new ModelMapper();
    public RoleDto convertToDto(RoleEntity entity) {
        return mapper.map(entity, RoleDto.class);
    }

    public RoleEntity convertToEntity(RoleDto dto) {
        return mapper.map(dto, RoleEntity.class);
    }

}

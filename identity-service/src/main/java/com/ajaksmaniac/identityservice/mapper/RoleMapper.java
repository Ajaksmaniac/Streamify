package com.ajaksmaniac.identityservice.mapper;

import com.ajaksmaniac.identityservice.dto.RoleDto;
import com.ajaksmaniac.identityservice.entity.RoleEntity;
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

package com.ajaksmaniac.streamify.mapper;

import com.ajaksmaniac.streamify.dto.RoleDto;
import com.ajaksmaniac.streamify.dto.UserDto;
import com.ajaksmaniac.streamify.entity.RoleEntity;
import com.ajaksmaniac.streamify.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class UserMapper {
    ModelMapper mapper = new ModelMapper();


    @Autowired
    RoleMapper roleMapper;

    public UserDto convertToDto(UserEntity entity) {

        return mapper.map(entity, UserDto.class);
    }


}

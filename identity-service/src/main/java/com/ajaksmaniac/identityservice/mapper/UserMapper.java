package com.ajaksmaniac.identityservice.mapper;

import com.ajaksmaniac.identityservice.dto.UserDto;
import com.ajaksmaniac.identityservice.entity.UserEntity;
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

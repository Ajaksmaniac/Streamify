package com.ajaksmaniac.streamify.notifications.mapper;

import com.ajaksmaniac.streamify.notifications.dto.UserDto;
import com.ajaksmaniac.streamify.notifications.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class UserMapper {
    ModelMapper mapper = new ModelMapper();


    public UserDto convertToDto(UserEntity entity) {

        return mapper.map(entity, UserDto.class);
    }
}


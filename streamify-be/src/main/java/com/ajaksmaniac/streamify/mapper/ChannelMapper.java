package com.ajaksmaniac.streamify.mapper;

import com.ajaksmaniac.streamify.dto.ChannelDto;
import com.ajaksmaniac.streamify.dto.ChannelOnlyDto;
import com.ajaksmaniac.streamify.dto.VideoDetailsDto;
import com.ajaksmaniac.streamify.entity.ChannelEntity;
import com.ajaksmaniac.streamify.entity.VideoDetailsEntity;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ChannelMapper {


    ModelMapper mapper = new ModelMapper();
    @Autowired
    VideoDetailsMapper videoDetailsMapper;
    public ChannelDto convertToDto(ChannelEntity entity) {
        ChannelDto dto = mapper.map(entity, ChannelDto.class);
        dto.setVideos(videoDetailsMapper.convertListToDTO(entity.getVideos()));
        log.info(entity.toString());
        dto.setUsername(entity.getUser().getUsername());

        return dto;
    }
    public ChannelOnlyDto convertToChannelOnlyDto(ChannelEntity entity) {
        ChannelOnlyDto dto = mapper.map(entity, ChannelOnlyDto.class);

        dto.setUsername(entity.getUser().getUsername());

        return dto;
    }
    public List<ChannelOnlyDto> convertListToChannelOnlyDTO(List<ChannelEntity> entityList ){
        return entityList.stream().map(this::convertToChannelOnlyDto
        ).collect(Collectors.toList());
    }

    public List<ChannelDto> convertListToDTO(List<ChannelEntity> entityList ){
        return entityList.stream().map(this::convertToDto
        ).collect(Collectors.toList());
    }

    public ChannelEntity convertToEntity(ChannelDto dto) {
        return mapper.map(dto, ChannelEntity.class);
    }

    public List<ChannelEntity> convertListToEntity(List<ChannelDto> dtoList ){
        return dtoList.stream().map(this::convertToEntity
        ).collect(Collectors.toList());
    }
}

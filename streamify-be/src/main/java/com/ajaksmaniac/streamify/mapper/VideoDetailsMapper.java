package com.ajaksmaniac.streamify.mapper;

import com.ajaksmaniac.streamify.dto.VideoDetailsDto;
import com.ajaksmaniac.streamify.entity.VideoDetailsEntity;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VideoDetailsMapper {

    ModelMapper mapper = new ModelMapper();
    public VideoDetailsDto convertToDto(VideoDetailsEntity entity) {

        VideoDetailsDto dto =mapper.map(entity, VideoDetailsDto.class);
//        if(entity.getChannel() != null){
//            dto.setChannelId(entity.getChannel().getId());
//        }

        return dto;

    }

    public List<VideoDetailsDto> convertListToDTO(List<VideoDetailsEntity> entityList ){
        return entityList.stream().map(this::convertToDto
        ).collect(Collectors.toList());
    }

    public VideoDetailsEntity convertToEntity(VideoDetailsDto dto) {
        return mapper.map(dto, VideoDetailsEntity.class);
    }

    public List<VideoDetailsEntity> convertListToEntity(List<VideoDetailsDto> dtoList ){
        return dtoList.stream().map(this::convertToEntity
        ).collect(Collectors.toList());
    }
}

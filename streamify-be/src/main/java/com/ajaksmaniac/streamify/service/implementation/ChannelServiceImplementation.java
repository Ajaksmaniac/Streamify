package com.ajaksmaniac.streamify.service.implementation;


import com.ajaksmaniac.streamify.dto.ChannelDto;
import com.ajaksmaniac.streamify.dto.VideoDetailsDto;
import com.ajaksmaniac.streamify.entity.*;
import com.ajaksmaniac.streamify.exception.*;
import com.ajaksmaniac.streamify.mapper.ChannelMapper;
import com.ajaksmaniac.streamify.mapper.VideoDetailsMapper;
import com.ajaksmaniac.streamify.repository.ChannelRepository;
import com.ajaksmaniac.streamify.repository.RoleRepository;
import com.ajaksmaniac.streamify.repository.UserRepository;
import com.ajaksmaniac.streamify.repository.VideoRepository;
import com.ajaksmaniac.streamify.service.ChannelService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ChannelServiceImplementation implements ChannelService {

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private VideoDetailsMapper videoDetailsMapper;

    @Autowired
    private ChannelMapper channelMapper;



    @Override
    public ChannelDto getChannelById(Long channelId) {
        if(!channelRepository.existsById(channelId)){
            throw new ChannelNotFoundException();
        }
        ChannelEntity en = channelRepository.getChannelById(channelId);
        List<VideoDetailsDto> videos = videoDetailsMapper.convertListToDTO(videoRepository.findByChannelId(channelId));


        return new ChannelDto(en.getId(), en.getChannelName(),en.getUser().getUsername(),videos);
    }

    @Override
    public void createChannel(ChannelDto channelDto) {
        if(channelRepository.existsByChannelName(channelDto.getChannelName())){
            throw new ChannelAlreadyExistsException();
        }

        if(!userRepository.existsByUsername(channelDto.getUsername())){
            throw new UserNotExistantException();
        }
        Optional<UserEntity> userEntity = userRepository.findByUsername(channelDto.getUsername());
        Optional<RoleEntity> roleEntity = roleRepository.findById(userEntity.get().getRole().getId());

        if(roleEntity.get().getName().equals("user")){
            throw new UserNotAlowedToCreateChannelsException();
        }

        ChannelEntity en = new ChannelEntity(null,channelDto.getChannelName(),userEntity.get(), null);
        channelRepository.save(en);

    }

    @Override
    public void updateChannel(ChannelDto channelDto) {
        if(!channelRepository.existsById(channelDto.getId())){
            throw new ChannelNotFoundException();
        }

        ChannelEntity entity = channelRepository.getById(channelDto.getId());

        if(!entity.getChannelName().equals(channelDto.getChannelName())){
            if(channelRepository.existsByChannelName(channelDto.getChannelName())){
                throw new ChannelAlreadyExistsException();
            }
        }


        if(!userRepository.existsByUsername(channelDto.getUsername())){
            throw new UserNotExistantException();
        }
        Optional<UserEntity> userEntity = userRepository.findByUsername(channelDto.getUsername());
        Optional<RoleEntity> roleEntity = roleRepository.findById(userEntity.get().getRole().getId());

        if(roleEntity.get().getName().equals("user")){
            throw new UserNotAlowedToCreateChannelsException();
        }

        List<VideoDetailsEntity> videos = videoDetailsMapper.convertListToEntity(channelDto.getVideos());


        ChannelEntity en = new ChannelEntity(null,channelDto.getChannelName(),userEntity.get(),videos);
        channelRepository.save(en);
    }

    @Override
    public void deleteById(Long id) {
        if(!channelRepository.existsById(id)){
            throw new ChannelNotFoundException();
        }

        channelRepository.deleteById(id);
    }

    @Override
    public List<ChannelDto> getAllChannels() {
        List<ChannelDto> channels = channelMapper.convertListToDTO(channelRepository.findAll());
        return channels;
    }

}

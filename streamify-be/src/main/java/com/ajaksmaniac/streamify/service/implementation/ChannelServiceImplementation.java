package com.ajaksmaniac.streamify.service.implementation;


import com.ajaksmaniac.streamify.dto.ChannelDto;
import com.ajaksmaniac.streamify.dto.VideoDetailsDto;
import com.ajaksmaniac.streamify.entity.*;
import com.ajaksmaniac.streamify.exception.channel.*;
import com.ajaksmaniac.streamify.exception.user.*;
import com.ajaksmaniac.streamify.mapper.ChannelMapper;
import com.ajaksmaniac.streamify.mapper.VideoDetailsMapper;
import com.ajaksmaniac.streamify.repository.ChannelRepository;
import com.ajaksmaniac.streamify.repository.RoleRepository;
import com.ajaksmaniac.streamify.repository.UserRepository;
import com.ajaksmaniac.streamify.repository.VideoRepository;
import com.ajaksmaniac.streamify.service.ChannelService;
import com.ajaksmaniac.streamify.util.UserUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ChannelServiceImplementation implements ChannelService {

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChannelMapper channelMapper;

    @Autowired
    UserUtil userUtil;


    @Override
    public ChannelDto getChannelById(Long channelId) {
        if(!channelRepository.existsById(channelId)){
            throw new ChannelNotFoundException();
        }
        ChannelEntity en = channelRepository.getChannelById(channelId);

        return channelMapper.convertToDto(en);
    }

    @Override
    public ChannelDto createChannel(ChannelDto channelDto) {

        if(channelRepository.existsByChannelName(channelDto.getChannelName())){
            throw new ChannelAlreadyExistsException();
        }

        if (!userUtil.isUserAdmin(sessionUser())) {
            if(!userUtil.isUserContentCreator(sessionUser())) throw new UserNotContentCreatorException();

            if(!channelDto.getUsername().equals(sessionUser().getUsername())) throw new UserNotPermittedToCreateChannelForOthersException();

        }

        Optional<UserEntity> chanelOwner = userRepository.findByUsername(channelDto.getUsername()) ;
        if(chanelOwner.isEmpty()){
            throw new UserNotExistantException();
        }

        ChannelEntity en = new ChannelEntity(null,channelDto.getChannelName(),chanelOwner.get(), null);
        return channelMapper.convertToDto(channelRepository.save(en));

    }

    @Override
    public ChannelDto updateChannel(ChannelDto channelDto) {
        if(!channelRepository.existsById(channelDto.getId())){
            throw new ChannelNotFoundException();
        }

        ChannelEntity entity = channelRepository.getById(channelDto.getId());
        if (!userUtil.isUserAdmin(sessionUser())) {
            if(!userUtil.isUserContentCreator(sessionUser())) throw new UserNotContentCreatorException();
            if(!entity.getUser().getUsername().equals(sessionUser().getUsername())) throw new UserNotPermittedToUpdateChannelForOthersException();

        }else{
            //Only Admin can change channel owners
            if(!userRepository.existsByUsername(channelDto.getUsername())) throw new UserNotExistantException();
            UserEntity userEntity = userRepository.findByUsername(channelDto.getUsername()).get();
            if(!sessionUser().getUsername().equals(channelDto.getUsername()) && !userUtil.isUserContentCreator(userEntity)){
                userEntity.setRole(new RoleEntity(2L,"content_creator"));
            }
            entity.setUser(userEntity);
        }

        if(channelRepository.existsByChannelName(channelDto.getChannelName())){
            throw new ChannelAlreadyExistsException();
        }

        entity.setChannelName(channelDto.getChannelName());

        return channelMapper.convertToDto(channelRepository.save(entity));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        if(!channelRepository.existsById(id)) {
            throw new ChannelNotFoundException();
        }

        if(!userUtil.isUserAdmin(sessionUser())){
            if(!userUtil.isUserContentCreator(sessionUser())) throw new UserNotContentCreatorException();

            List<ChannelEntity> channels =channelRepository.findByUserId(sessionUser().getId()).stream().filter(c -> Objects.equals(c.getId(), id)).toList();
            if(channels.isEmpty()) throw new UserNotPermittedToDeleteChannelForOthersException();
        }
//        videoRepository.deleteAllVideosDetailsByChannelId(id);

        channelRepository.deleteById(id);
    }

    @Override
    public List<ChannelDto> getAllChannels() {
        return channelMapper.convertListToDTO(channelRepository.findAll());
    }

    private UserEntity sessionUser() {
        return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}

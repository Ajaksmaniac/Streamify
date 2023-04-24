package com.ajaksmaniac.streamify.service.implementation;


import com.ajaksmaniac.streamify.dto.ChannelDto;
import com.ajaksmaniac.streamify.entity.*;
import com.ajaksmaniac.streamify.exception.channel.*;
import com.ajaksmaniac.streamify.exception.user.*;
import com.ajaksmaniac.streamify.mapper.ChannelMapper;
import com.ajaksmaniac.streamify.repository.ChannelRepository;
import com.ajaksmaniac.streamify.repository.UserRepository;
import com.ajaksmaniac.streamify.service.ChannelService;
import com.ajaksmaniac.streamify.util.UserUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

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
        Optional<ChannelEntity> en = channelRepository.getChannelById(channelId);
        if(en.isEmpty()){
            throw new ChannelNotFoundException(channelId);
        }

        return channelMapper.convertToDto(en.get());
    }

    @Override
    public List<ChannelDto> getChannelByUserId(Long userId) {
        List<ChannelEntity> en = channelRepository.findByUserId(userId);
        if(en.isEmpty()){
            return Collections.emptyList();
        }

        return channelMapper.convertListToDTO(en);
    }

    @Override
    public ChannelDto createChannel(ChannelDto channelDto) {

        if(channelRepository.existsByChannelName(channelDto.getChannelName())){
            throw new ChannelAlreadyExistsException(channelDto.getChannelName());
        }

        Optional<UserEntity> chanelOwner = userRepository.findByUsername(channelDto.getUsername()) ;
        if(!chanelOwner.isPresent()){
            throw new UserNotExistentException(channelDto.getUsername());
        }

        if (!userUtil.isUserAdmin(sessionUser())) {
            if(!userUtil.isUserContentCreator(sessionUser())) throw new UserNotContentCreatorException(sessionUser().getId());

            if(!channelDto.getUsername().equals(sessionUser().getUsername())) throw new UserNotPermittedToCreateChannelForOthersException(sessionUser().getId());

        }


        ChannelEntity en = new ChannelEntity(null,channelDto.getChannelName(),chanelOwner.get(), null);
        return channelMapper.convertToDto(channelRepository.save(en));

    }

    @Override
    public ChannelDto updateChannel(ChannelDto channelDto) {
        Optional<ChannelEntity> entity = channelRepository.findById(channelDto.getId());

        if(entity.isEmpty()){
            throw new ChannelNotFoundException(channelDto.getId());
        }


        if (!userUtil.isUserAdmin(sessionUser())) {
            if(!userUtil.isUserContentCreator(sessionUser())) throw new UserNotContentCreatorException(sessionUser().getId());
            if(!entity.get().getUser().getUsername().equals(sessionUser().getUsername())) throw new UserNotPermittedToUpdateChannelForOthersException(sessionUser().getId());

        }else{
            //Only Admin can change channel owners
            if(!userRepository.existsByUsername(channelDto.getUsername())) throw new UserNotExistentException(channelDto.getUsername());
            UserEntity userEntity = userRepository.findByUsername(channelDto.getUsername()).get();
            if(!sessionUser().getUsername().equals(channelDto.getUsername()) && !userUtil.isUserContentCreator(userEntity)){
                userEntity.setRole(new RoleEntity(2L));
            }
            entity.get().setUser(userEntity);
        }

        if(channelRepository.existsByChannelName(channelDto.getChannelName())){
            throw new ChannelAlreadyExistsException(channelDto.getChannelName());
        }

        entity.get().setChannelName(channelDto.getChannelName());

        return channelMapper.convertToDto(channelRepository.save(entity.get()));
    }

    @Override
    public void deleteById(Long id) {

        if(!channelRepository.existsById(id)) {
            throw new ChannelNotFoundException(id);
        }

        if(!userUtil.isUserAdmin(sessionUser())){
            if(!userUtil.isUserContentCreator(sessionUser())) throw new UserNotContentCreatorException(sessionUser().getId());

            List<ChannelEntity> channels =channelRepository.findByUserId(sessionUser().getId()).stream().filter(c -> Objects.equals(c.getId(), id)).toList();
            if(channels.isEmpty()) throw new UserNotPermittedToDeleteChannelForOthersException(sessionUser().getId());
        }

        channelRepository.deleteById(id);
    }

    @Override
    public List<ChannelDto> getAllChannels() {
        return channelMapper.convertListToDTO(channelRepository.findAll());
    }

    @Override
    public List<ChannelDto> search(String keywords) {
        Map channelMap = new HashMap<Long, ChannelEntity>();
        List<String> keywordsList = Arrays.stream(keywords.split(" ")).toList();
        keywordsList.forEach(kw ->{
            channelRepository.search(kw).forEach(v->{
                if(!channelMap.containsKey(v.getId())){
                    channelMap.put(v.getId(),v);
                }
            });
        });

        return channelMapper.convertListToDTO(channelMap.values().stream().toList());
    }

    private UserEntity sessionUser() {
        return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}

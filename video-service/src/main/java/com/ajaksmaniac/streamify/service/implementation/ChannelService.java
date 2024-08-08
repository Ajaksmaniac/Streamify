package com.ajaksmaniac.streamify.service.implementation;


import com.ajaksmaniac.streamify.dto.ChannelDto;
import com.ajaksmaniac.streamify.entity.*;
import com.ajaksmaniac.streamify.exception.channel.*;
import com.ajaksmaniac.streamify.exception.user.*;
import com.ajaksmaniac.streamify.mapper.ChannelMapper;
import com.ajaksmaniac.streamify.repository.ChannelRepository;
import com.ajaksmaniac.streamify.repository.UserRepository;
import com.ajaksmaniac.streamify.util.UserUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class ChannelService {

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChannelMapper channelMapper;

    @Autowired
    UserUtil userUtil;


//    @Override
    public ChannelDto getChannelById(Long channelId) {
        Optional<ChannelEntity> en = channelRepository.getChannelById(channelId);
        if(en.isEmpty()){
            throw new ChannelNotFoundException(channelId);
        }

        return channelMapper.convertToDto(en.get());
    }

//    @Override
    public List<ChannelDto> getChannelByUserId(Long userId) {
        List<ChannelEntity> en = channelRepository.findByUserId(userId);
        if(en.isEmpty()){
            return Collections.emptyList();
        }

        return channelMapper.convertListToDTO(en);
    }

//    @Override
    public ChannelDto createChannel(ChannelDto channelDto,Long authenticatedUserId) {

        if(channelRepository.existsByChannelName(channelDto.getChannelName())){
            throw new ChannelAlreadyExistsException(channelDto.getChannelName());
        }

        Optional<UserEntity> chanelOwner = userRepository.findByUsername(channelDto.getUsername()) ;
        if(!chanelOwner.isPresent()){
            throw new UserNotExistentException(channelDto.getUsername());
        }

        if (!userUtil.isUserAdmin(sessionUser(authenticatedUserId))) {
            if(!userUtil.isUserContentCreator(sessionUser(authenticatedUserId))) throw new UserNotContentCreatorException(sessionUser(authenticatedUserId).getId());

            if(!channelDto.getUsername().equals(sessionUser(authenticatedUserId).getUsername())) throw new UserNotPermittedToCreateChannelForOthersException(sessionUser(authenticatedUserId).getId());

        }


        ChannelEntity en = new ChannelEntity(null,channelDto.getChannelName(),chanelOwner.get(),null, null);
        return channelMapper.convertToDto(channelRepository.save(en));

    }

//    @Override
    public ChannelDto updateChannel(ChannelDto channelDto,Long authenticatedUserId) {
        Optional<ChannelEntity> entity = channelRepository.findById(channelDto.getId());

        if(entity.isEmpty()){
            throw new ChannelNotFoundException(channelDto.getId());
        }


        if (!userUtil.isUserAdmin(sessionUser(authenticatedUserId))) {
            if(!userUtil.isUserContentCreator(sessionUser(authenticatedUserId))) throw new UserNotContentCreatorException(sessionUser(authenticatedUserId).getId());
            if(!entity.get().getUser().getUsername().equals(sessionUser(authenticatedUserId).getUsername())) throw new UserNotPermittedToUpdateChannelForOthersException(sessionUser(authenticatedUserId).getId());

        }else{
            //Only Admin can change channel owners
            if(!userRepository.existsByUsername(channelDto.getUsername())) throw new UserNotExistentException(channelDto.getUsername());
            UserEntity userEntity = userRepository.findByUsername(channelDto.getUsername()).get();
            if(!sessionUser(authenticatedUserId).getUsername().equals(channelDto.getUsername()) && !userUtil.isUserContentCreator(userEntity)){
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

//    @Override
    public void deleteById(Long id,Long authenticatedUserId) {

        if(!channelRepository.existsById(id)) {
            throw new ChannelNotFoundException(id);
        }

        if(!userUtil.isUserAdmin(sessionUser(authenticatedUserId))){
            if(!userUtil.isUserContentCreator(sessionUser(authenticatedUserId))) throw new UserNotContentCreatorException(sessionUser(authenticatedUserId).getId());

            List<ChannelEntity> channels =channelRepository.findByUserId(sessionUser(authenticatedUserId).getId()).stream().filter(c -> Objects.equals(c.getId(), id)).toList();
            if(channels.isEmpty()) throw new UserNotPermittedToDeleteChannelForOthersException(sessionUser(authenticatedUserId).getId());
        }

        channelRepository.deleteById(id);
    }

//    @Override
    public List<ChannelDto> getAllChannels() {
        return channelMapper.convertListToDTO(channelRepository.findAll());
    }

//    @Override
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



//    @Override
    public void subscribeToChannel(Long userId, Long channelId){
        Optional< UserEntity > user = Optional.ofNullable(userRepository.findById(userId).orElseThrow(() ->
                new UserNotExistentException(String.valueOf(userId))));

        Optional< ChannelEntity > channel = Optional.ofNullable(channelRepository.findById(channelId).orElseThrow(() ->
                new ChannelNotFoundException(channelId)));
        if(user.get().getSubscribedChannels().contains(channel.get())){
            throw new AlreadySubscribedException(channel.get().getChannelName());
        }
        user.get().subscribe(channel.get());
        userRepository.save(user.get());
    }

//    @Override
    public void unsubscribeFromChannel(Long userId, Long channelId){
        Optional< UserEntity > user = Optional.ofNullable(userRepository.findById(userId).orElseThrow(() ->
                new UserNotExistentException(String.valueOf(userId))));

        Optional< ChannelEntity > channel = Optional.ofNullable(channelRepository.findById(channelId).orElseThrow(() ->
                new ChannelNotFoundException(channelId)));
        if(!user.get().getSubscribedChannels().contains(channel.get())){
            throw new NotSubscribedException(channel.get().getChannelName());
        }
        user.get().unsubscribe(channel.get());
        userRepository.save(user.get());
    }

    private UserEntity sessionUser(Long userId) {
        Optional< UserEntity > user = Optional.ofNullable(userRepository.findById(userId).orElseThrow(() ->
                new UserNotExistentException(String.valueOf(userId))));
        return user.get();
    }
}

package com.ajaksmaniac.streamify.unit.service;

import com.ajaksmaniac.streamify.dto.ChannelDto;
import com.ajaksmaniac.streamify.entity.ChannelEntity;
import com.ajaksmaniac.streamify.entity.RoleEntity;
import com.ajaksmaniac.streamify.entity.UserEntity;
import com.ajaksmaniac.streamify.exception.channel.*;
import com.ajaksmaniac.streamify.exception.user.*;
import com.ajaksmaniac.streamify.mapper.ChannelMapper;
import com.ajaksmaniac.streamify.repository.ChannelRepository;
import com.ajaksmaniac.streamify.repository.UserRepository;
import com.ajaksmaniac.streamify.service.implementation.ChannelService;
import com.ajaksmaniac.streamify.util.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@RunWith(PowerMockRunner.class)
@PrepareForTest(ChannelService.class)
class ChannelServiceTest {

    @Mock
    private ChannelRepository channelRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ChannelMapper channelMapper;

    @Mock
    private UserUtil userUtil;

    @InjectMocks
    private ChannelService channelService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetChannelById() {
        Long channelId = 1L;
        ChannelEntity channelEntity = new ChannelEntity();
        ChannelDto channelDto = new ChannelDto();

        when(channelRepository.getChannelById(channelId)).thenReturn(Optional.of(channelEntity));
        when(channelMapper.convertToDto(channelEntity)).thenReturn(channelDto);

        ChannelDto result = channelService.getChannelById(channelId);

        assertEquals(channelDto, result);
    }

    @Test
    void testGetChannelById_ChannelNotFound() {
        Long channelId = 1L;

        when(channelRepository.getChannelById(channelId)).thenReturn(Optional.empty());

        assertThrows(ChannelNotFoundException.class, () -> channelService.getChannelById(channelId));
    }

    @Test
    void testGetChannelByUserId() {
        Long userId = 1L;
        List<ChannelEntity> channelEntities = Arrays.asList(new ChannelEntity(), new ChannelEntity());
        List<ChannelDto> channelDtos = Arrays.asList(new ChannelDto(), new ChannelDto());

        when(channelRepository.findByUserId(userId)).thenReturn(channelEntities);
        when(channelMapper.convertListToDTO(channelEntities)).thenReturn(channelDtos);

        List<ChannelDto> result = channelService.getChannelByUserId(userId);

        assertEquals(channelDtos, result);
    }

    @Test
    void testGetChannelByUserId_EmptyList() {
        Long userId = 1L;

        when(channelRepository.findByUserId(userId)).thenReturn(Collections.emptyList());

        List<ChannelDto> result = channelService.getChannelByUserId(userId);

        assertTrue(result.isEmpty());
    }

    @Test
    void testCreateChannel() {
        Long authenticatedUserId = 1L;
        ChannelDto channelDto = new ChannelDto();
        channelDto.setChannelName("TestChannel");
        channelDto.setUsername("testuser");

        ChannelEntity channelEntity = new ChannelEntity();
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testuser");

        when(channelRepository.existsByChannelName(channelDto.getChannelName())).thenReturn(false);
        when(userRepository.findByUsername(channelDto.getUsername())).thenReturn(Optional.of(userEntity));
        when(userUtil.isUserAdmin(any())).thenReturn(false);
        when(userUtil.isUserContentCreator(any())).thenReturn(true);
        when(userRepository.findById(authenticatedUserId)).thenReturn(Optional.of(userEntity));
        when(channelRepository.save(any(ChannelEntity.class))).thenReturn(channelEntity);
        when(channelMapper.convertToDto(channelEntity)).thenReturn(channelDto);

        ChannelDto result = channelService.createChannel(channelDto, authenticatedUserId);

        assertEquals(channelDto, result);
    }

    @Test
    void testCreateChannel_ChannelAlreadyExists() {
        ChannelDto channelDto = new ChannelDto();
        channelDto.setChannelName("TestChannel");

        when(channelRepository.existsByChannelName(channelDto.getChannelName())).thenReturn(true);

        assertThrows(ChannelAlreadyExistsException.class, () -> channelService.createChannel(channelDto, 1L));
    }

    @Test
    void testCreateChannel_UserNotExistent() {
        ChannelDto channelDto = new ChannelDto();
        channelDto.setUsername("nonexistent");

        when(channelRepository.existsByChannelName(channelDto.getChannelName())).thenReturn(false);
        when(userRepository.findByUsername(channelDto.getUsername())).thenReturn(Optional.empty());

        assertThrows(UserNotExistentException.class, () -> channelService.createChannel(channelDto, 1L));
    }

    @Test
    void testUpdateChannel() {
        Long authenticatedUserId = 1L;
        ChannelDto channelDto = new ChannelDto();
        channelDto.setId(1L);
        channelDto.setChannelName("UpdatedChannel");
        channelDto.setUsername("testuser");

        ChannelEntity channelEntity = new ChannelEntity();
        channelEntity.setId(1L);

        UserEntity mockUser = new UserEntity();
        channelEntity.setUser(mockUser);
        mockUser.setUsername("test");
        mockUser.setId(1L);
        when(channelRepository.findById(channelDto.getId())).thenReturn(Optional.of(channelEntity));
        when(userUtil.isUserAdmin(any())).thenReturn(false);
        when(userUtil.isUserContentCreator(any())).thenReturn(true);
        when(userRepository.findById(authenticatedUserId)).thenReturn(Optional.of(mockUser));
        when(channelRepository.existsByChannelName(channelDto.getChannelName())).thenReturn(false);
        when(channelRepository.save(channelEntity)).thenReturn(channelEntity);
        when(channelMapper.convertToDto(channelEntity)).thenReturn(channelDto);

        ChannelDto result = channelService.updateChannel(channelDto, authenticatedUserId);

        assertEquals(channelDto, result);
    }

    @Test
    void testUpdateChannel_ChannelNotFound() {
        ChannelDto channelDto = new ChannelDto();
        channelDto.setId(1L);

        when(channelRepository.findById(channelDto.getId())).thenReturn(Optional.empty());

        assertThrows(ChannelNotFoundException.class, () -> channelService.updateChannel(channelDto, 1L));
    }

    @Test
    void testUpdateChannel_ChannelAlreadyExists() {
        ChannelDto channelDto = new ChannelDto();
        channelDto.setId(1L);
        channelDto.setChannelName("UpdatedChannel");

        ChannelEntity channelEntity = new ChannelEntity();
        channelEntity.setId(1L);
        UserEntity mockUser = new UserEntity();
        channelEntity.setUser(mockUser);
        mockUser.setUsername("test");
        mockUser.setId(1L);
        when(channelRepository.findById(channelDto.getId())).thenReturn(Optional.of(channelEntity));
        when(userUtil.isUserAdmin(any())).thenReturn(false);
        when(userUtil.isUserContentCreator(any())).thenReturn(true);
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(channelRepository.existsByChannelName(channelDto.getChannelName())).thenReturn(true);

        assertThrows(ChannelAlreadyExistsException.class, () -> channelService.updateChannel(channelDto, 1L));
    }

    @Test
    void testDeleteById() {
        Long id = 1L;
        Long authenticatedUserId = 1L;

        when(channelRepository.existsById(id)).thenReturn(true);
        when(userUtil.isUserAdmin(any())).thenReturn(false);
        when(userUtil.isUserContentCreator(any())).thenReturn(true);
        when(userRepository.findById(authenticatedUserId)).thenReturn(Optional.of(new UserEntity()));
        when(channelRepository.findByUserId(any())).thenReturn(Collections.singletonList(new ChannelEntity(1L)));
        channelService.deleteById(id, authenticatedUserId);

        verify(channelRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteById_ChannelNotFound() {
        Long id = 1L;

        when(channelRepository.existsById(id)).thenReturn(false);

        assertThrows(ChannelNotFoundException.class, () -> channelService.deleteById(id, 1L));
    }

    @Test
    void testGetAllChannels() {
        List<ChannelEntity> channelEntities = Arrays.asList(new ChannelEntity(), new ChannelEntity());
        List<ChannelDto> channelDtos = Arrays.asList(new ChannelDto(), new ChannelDto());

        when(channelRepository.findAll()).thenReturn(channelEntities);
        when(channelMapper.convertListToDTO(channelEntities)).thenReturn(channelDtos);

        List<ChannelDto> result = channelService.getAllChannels();

        assertEquals(channelDtos, result);
    }

    @Test
    void testSearch() {
        String keywords = "test";
        ChannelEntity channelEntity = new ChannelEntity();
        channelEntity.setId(1L);
        channelEntity.setChannelName("test channel");

        when(channelRepository.search("test")).thenReturn(Arrays.asList(channelEntity));
        when(channelMapper.convertListToDTO(anyList())).thenReturn(Arrays.asList(new ChannelDto()));

        List<ChannelDto> result = channelService.search(keywords);

        assertFalse(result.isEmpty());
    }

    @Test
    void testSubscribeToChannel() {
        Long userId = 1L;
        Long channelId = 1L;

        UserEntity user = new UserEntity();
        user.setSubscribedChannels(new ArrayList<>());
        ChannelEntity channel = new ChannelEntity();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(channelRepository.findById(channelId)).thenReturn(Optional.of(channel));

        channelService.subscribeToChannel(userId, channelId);

        assertTrue(user.getSubscribedChannels().contains(channel));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUnsubscribeFromChannel() {
        Long userId = 1L;
        Long channelId = 1L;

        UserEntity user = new UserEntity();
        user.setSubscribedChannels(new ArrayList<>());
        ChannelEntity channel = new ChannelEntity();
        user.subscribe(channel);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(channelRepository.findById(channelId)).thenReturn(Optional.of(channel));

        channelService.unsubscribeFromChannel(userId, channelId);

        assertFalse(user.getSubscribedChannels().contains(channel));
        verify(userRepository, times(1)).save(user);
    }

    private UserEntity sessionUser(Long userId) {
        Optional<UserEntity> user = Optional.ofNullable(userRepository.findById(userId).orElseThrow(() ->
                new UserNotExistentException(String.valueOf(userId))));
        return user.get();
    }
}

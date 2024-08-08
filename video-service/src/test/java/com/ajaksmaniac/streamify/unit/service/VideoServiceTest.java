package com.ajaksmaniac.streamify.unit.service;

import com.ajaksmaniac.streamify.dto.VideoDetailsDto;
import com.ajaksmaniac.streamify.entity.ChannelEntity;
import com.ajaksmaniac.streamify.entity.UserEntity;
import com.ajaksmaniac.streamify.entity.VideoDetailsEntity;
import com.ajaksmaniac.streamify.exception.channel.ChannelNotFoundException;
import com.ajaksmaniac.streamify.exception.user.UserNotContentCreatorException;
import com.ajaksmaniac.streamify.exception.video.*;
import com.ajaksmaniac.streamify.mapper.VideoDetailsMapper;
import com.ajaksmaniac.streamify.repository.ChannelRepository;
import com.ajaksmaniac.streamify.repository.UserRepository;
import com.ajaksmaniac.streamify.repository.VideoRepository;
import com.ajaksmaniac.streamify.service.implementation.VideoService;
import com.ajaksmaniac.streamify.util.UserUtil;
import com.ajaksmaniac.streamify.util.VideoUtilService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({VideoService.class, VideoUtilService.class})
class VideoServiceTest {

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ChannelRepository channelRepository;

    @Mock
    private VideoDetailsMapper mapper;

    @Mock
    private UserUtil userUtil;

    @InjectMocks
    private VideoService videoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void testGetVideo() throws Exception {
//        Long videoId = 1L;
//
//        when(videoRepository.existsById(videoId)).thenReturn(true);
//
//        Resource resource = mock(Resource.class);
//        VideoUtilService videoUtilServiceMock = mock(VideoUtilService.class, RETURNS_DEEP_STUBS);
//        when(videoUtilServiceMock.getFileAsResource(any())).thenReturn(resource);
//
//
//        PowerMockito.whenNew(VideoUtilService.class).withAnyArguments().thenReturn(videoUtilServiceMock);
//
//        Resource result = videoService.getVideo(videoId);
//
//        assertEquals(resource, result);
//    }

    @Test
    void testGetVideo_VideoNotFound() {
        Long videoId = 1L;

        when(videoRepository.existsById(videoId)).thenReturn(false);

        assertThrows(VideoNotFoundException.class, () -> videoService.getVideo(videoId));
    }

    @Test
    void testGetVideoDetails() {
        Long videoId = 1L;
        VideoDetailsEntity videoEntity = new VideoDetailsEntity();
        VideoDetailsDto videoDto = new VideoDetailsDto();

        when(videoRepository.existsById(videoId)).thenReturn(true);
        when(videoRepository.findById(videoId)).thenReturn(Optional.of(videoEntity));
        when(mapper.convertToDto(videoEntity)).thenReturn(videoDto);

        VideoDetailsDto result = videoService.getVideoDetails(videoId);

        assertEquals(videoDto, result);
    }

    @Test
    void testGetVideoDetails_VideoNotFound() {
        Long videoId = 1L;

        when(videoRepository.existsById(videoId)).thenReturn(false);

        assertThrows(VideoNotFoundException.class, () -> videoService.getVideoDetails(videoId));
    }

    @Test
    void testGetAllVideosDetails() {
        List<VideoDetailsEntity> videoEntities = Arrays.asList(new VideoDetailsEntity(), new VideoDetailsEntity());
        List<VideoDetailsDto> videoDtos = Arrays.asList(new VideoDetailsDto(), new VideoDetailsDto());

        when(videoRepository.getAllVideos()).thenReturn(videoEntities);
        when(mapper.convertListToDTO(videoEntities)).thenReturn(videoDtos);

        List<VideoDetailsDto> result = videoService.getAllVideosDetails();

        assertEquals(videoDtos, result);
    }

//    @Test
//    void testSaveVideo() throws IOException {
//        Long authenticatedUserId = 1L;
//        Long channelId = 1L;
//        String videoName = "TestVideo";
//        String description = "TestDescription";
//        MultipartFile file = mock(MultipartFile.class);
//        ChannelEntity channelEntity = new ChannelEntity();
//        UserEntity userEntity = new UserEntity();
//        userEntity.setId(authenticatedUserId);
//        VideoDetailsEntity videoEntity = new VideoDetailsEntity(videoName, channelEntity, java.sql.Date.valueOf(LocalDate.now()), description);
//        videoEntity.setId(1L);
//        VideoDetailsDto videoDto = new VideoDetailsDto();
//
//        when(channelRepository.existsById(channelId)).thenReturn(true);
//        when(userUtil.isUserAdmin(any())).thenReturn(true);
//        when(userUtil.isUserContentCreator(any())).thenReturn(true);
//        when(channelRepository.isChannelOwnedByUser(channelId, userEntity)).thenReturn(true);
//        when(videoRepository.existsByName(videoName)).thenReturn(false);
//        when(videoRepository.save(any(VideoDetailsEntity.class))).thenReturn(videoEntity);
//        when(mapper.convertToDto(videoEntity)).thenReturn(videoDto);
//        when(userRepository.findById(authenticatedUserId)).thenReturn(Optional.of(userEntity));
////        PowerMockito.mockStatic(VideoUtilService.class);
//        PowerMockito.doNothing().when(VideoUtilService.class);
//        try (MockedStatic<VideoUtilService> utilClass = Mockito.mockStatic(VideoUtilService.class)) {
////            doNothing().when(VideoUtilService).thenReturn(null).
//           utilClass.when(VideoUtilService.saveFile(any(),any(),any())).thenReturn()
//        }
//        VideoDetailsDto result = videoService.saveVideo(file, videoName, channelId, description, authenticatedUserId);
//
//
//        assertEquals(videoDto, result);
//    }

    @Test
    void testSaveVideo_ChannelNotFound() {
        Long channelId = 1L;
        MultipartFile file = mock(MultipartFile.class);

        when(channelRepository.existsById(channelId)).thenReturn(false);

        assertThrows(ChannelNotFoundException.class, () -> videoService.saveVideo(file, "TestVideo", channelId, "TestDescription", 1L));
    }

    @Test
    void testSaveVideo_UserNotContentCreator() {
        Long authenticatedUserId = 1L;
        Long channelId = 1L;
        MultipartFile file = mock(MultipartFile.class);
        UserEntity loggedUser = new UserEntity();

        loggedUser.setId(authenticatedUserId);
        when(channelRepository.existsById(channelId)).thenReturn(true);
        when(userUtil.isUserAdmin(any())).thenReturn(false);
        when(userUtil.isUserContentCreator(any())).thenReturn(false);
        when(userRepository.findById(authenticatedUserId)).thenReturn(Optional.of(loggedUser));

        assertThrows(UserNotContentCreatorException.class, () -> videoService.saveVideo(file, "TestVideo", channelId, "TestDescription", authenticatedUserId));
    }

    @Test
    void testSaveVideo_VideoAlreadyExists() {
        Long authenticatedUserId = 1L;
        Long channelId = 1L;
        String videoName = "TestVideo";
        MultipartFile file = mock(MultipartFile.class);
        UserEntity userEntity = new UserEntity();
        userEntity.setId(authenticatedUserId);
        when(channelRepository.existsById(channelId)).thenReturn(true);
        when(userUtil.isUserAdmin(any())).thenReturn(false);
        when(userUtil.isUserContentCreator(any())).thenReturn(true);
        when(channelRepository.isChannelOwnedByUser(channelId, userEntity)).thenReturn(true);
        when(videoRepository.existsByName(videoName)).thenReturn(true);
        when(userRepository.findById(authenticatedUserId)).thenReturn(Optional.of(userEntity));

        assertThrows(VideoAlreadyExistsException.class, () -> videoService.saveVideo(file, videoName, channelId, "TestDescription", authenticatedUserId));
    }

    @Test
    void testDeleteVideo() throws IOException {
        Long videoId = 1L;
        Long authenticatedUserId = 1L;
        UserEntity userEntity = new UserEntity();
        userEntity.setId(authenticatedUserId);
        when(videoRepository.existsById(videoId)).thenReturn(true);
        when(userUtil.isUserAdmin(any())).thenReturn(false);
        when(userUtil.isUserContentCreator(any())).thenReturn(true);
        when(videoRepository.isVideoOwnedByUser(videoId, authenticatedUserId)).thenReturn(true);
        when(userRepository.findById(authenticatedUserId)).thenReturn(Optional.of(userEntity));
        videoService.deleteVideo(videoId, authenticatedUserId);

        verify(videoRepository, times(1)).deleteById(videoId);
    }

    @Test
    void testDeleteVideo_VideoNotFound() {
        Long videoId = 1L;

        when(videoRepository.existsById(videoId)).thenReturn(false);

        assertThrows(VideoNotFoundException.class, () -> videoService.deleteVideo(videoId, 1L));
    }

    @Test
    void testDeleteVideo_UserNotPermitted() {
        Long videoId = 1L;
        Long authenticatedUserId = 1L;
        UserEntity loggedUser = new UserEntity();
        when(videoRepository.existsById(videoId)).thenReturn(true);
        when(userUtil.isUserAdmin(any())).thenReturn(false);
        when(userUtil.isUserContentCreator(any())).thenReturn(true);
        when(videoRepository.isVideoOwnedByUser(videoId, authenticatedUserId)).thenReturn(false);
        when(userRepository.findById(authenticatedUserId)).thenReturn(Optional.of(loggedUser));
        assertThrows(UserNotPermittedToDeleteVideoException.class, () -> videoService.deleteVideo(videoId, authenticatedUserId));
    }

    @Test
    void testUpdateVideo() throws IOException {
        Long videoId = 1L;
        Long authenticatedUserId = 1L;
        String newName = "UpdatedVideo";
        String newDescription = "UpdatedDescription";
        MultipartFile file = mock(MultipartFile.class);
        VideoDetailsEntity videoEntity = new VideoDetailsEntity();
        VideoDetailsDto videoDto = new VideoDetailsDto();
        UserEntity loggedUser = new UserEntity();
        loggedUser.setId(authenticatedUserId);
        when(videoRepository.findById(videoId)).thenReturn(Optional.of(videoEntity));
        when(userUtil.isUserAdmin(any())).thenReturn(false);
        when(userUtil.isUserContentCreator(any())).thenReturn(true);
        when(videoRepository.isVideoOwnedByUser(videoId, authenticatedUserId)).thenReturn(true);
        when(videoRepository.existsByName(newName)).thenReturn(false);
        when(mapper.convertToDto(videoEntity)).thenReturn(videoDto);
        when(userRepository.findById(authenticatedUserId)).thenReturn(Optional.of(loggedUser));
        VideoDetailsDto result = videoService.updateVideo(videoId, newName, newDescription, null, authenticatedUserId);

        assertEquals(videoDto, result);
    }

    @Test
    void testUpdateVideo_VideoNotFound() {
        Long videoId = 1L;

        when(videoRepository.findById(videoId)).thenReturn(Optional.empty());

        assertThrows(VideoNotFoundException.class, () -> videoService.updateVideo(videoId, "UpdatedVideo", "UpdatedDescription", null, 1L));
    }

    @Test
    void testUpdateVideo_UserNotPermitted() {
        Long videoId = 1L;
        Long authenticatedUserId = 1L;
        VideoDetailsEntity videoEntity = new VideoDetailsEntity();
        UserEntity loggedUser = new UserEntity();
        loggedUser.setId(authenticatedUserId);
        when(videoRepository.findById(videoId)).thenReturn(Optional.of(videoEntity));
        when(userUtil.isUserAdmin(any())).thenReturn(false);
        when(userUtil.isUserContentCreator(any())).thenReturn(true);
        when(videoRepository.isVideoOwnedByUser(videoId, authenticatedUserId)).thenReturn(false);
        when(userRepository.findById(authenticatedUserId)).thenReturn(Optional.of(loggedUser));

        assertThrows(UserNotPermittedToUpdateVideoException.class, () -> videoService.updateVideo(videoId, "UpdatedVideo", "UpdatedDescription", null, authenticatedUserId));
    }

    @Test
    void testSearch() {
        String keywords = "test video";
        List<VideoDetailsEntity> videoEntities = Arrays.asList(new VideoDetailsEntity(), new VideoDetailsEntity());
        List<VideoDetailsDto> videoDtos = Arrays.asList(new VideoDetailsDto(), new VideoDetailsDto());

        when(videoRepository.search(anyString())).thenReturn(videoEntities);
        when(mapper.convertListToDTO(anyList())).thenReturn(videoDtos);

        List<VideoDetailsDto> result = videoService.search(keywords);

        assertEquals(videoDtos, result);
    }

    @Test
    void testGetAllVideosByChannel() {
        Long channelId = 1L;
        List<VideoDetailsEntity> videoEntities = Arrays.asList(new VideoDetailsEntity(), new VideoDetailsEntity());
        List<VideoDetailsDto> videoDtos = Arrays.asList(new VideoDetailsDto(), new VideoDetailsDto());

        when(videoRepository.getVideosForChannel(channelId)).thenReturn(videoEntities);
        when(mapper.convertListToDTO(videoEntities)).thenReturn(videoDtos);

        List<VideoDetailsDto> result = videoService.getAllVideosByChannel(channelId);

        assertEquals(videoDtos, result);
    }
}

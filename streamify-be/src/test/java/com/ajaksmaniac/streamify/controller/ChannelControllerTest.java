package com.ajaksmaniac.streamify.controller;


import com.ajaksmaniac.streamify.dto.ChannelDto;
import com.ajaksmaniac.streamify.entity.ChannelEntity;
import com.ajaksmaniac.streamify.entity.UserEntity;
import com.ajaksmaniac.streamify.repository.ChannelRepository;
import com.ajaksmaniac.streamify.repository.UserRepository;
import com.ajaksmaniac.streamify.service.ChannelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(ChannelController.class)
public class ChannelControllerTest {

    @InjectMocks
    ChannelController channelController;


    @MockBean
    @Qualifier("channelServiceImplementation")
    private ChannelService channelService;

    @MockBean
    private ChannelRepository channelRepository;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetChannelDetailsById()  {
        // arrange
        Long id = 1L;
        UserEntity userEntity = new UserEntity("test","test");
        ChannelEntity channelEntity = new ChannelEntity(1L,"testChannel",userEntity, null,null);
        when(userRepository.existsByUsername("test")).thenReturn(true);
        when(userRepository.findByUsername("test")).thenReturn(Optional.of(userEntity));

        when(channelRepository.getChannelById(id)).thenReturn(Optional.ofNullable(channelEntity));
        when(channelService.getChannelById(id)).thenReturn(new ChannelDto(1L,"test","testChannel"));

        // act
        ResponseEntity<ChannelDto> responseEntity = channelController.getChannelDetailsById(id);

        // assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(id, Objects.requireNonNull(responseEntity.getBody()).getId());
//        assertEquals("testChannel", Objects.requireNonNull(responseEntity.getBody()).getChannelName());
        assertEquals(id, responseEntity.getBody().getId());
        assertEquals("testChannel", responseEntity.getBody().getChannelName());


    }

    @Test
    void testSaveChannel(){
        // arrange
        ChannelDto channelDto = new ChannelDto();
        doNothing().when(channelService).createChannel(channelDto,1l);

        // act
        ResponseEntity<ChannelDto> responseEntity = channelController.saveChannel(channelDto, 1L);

        // assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Channel saved", responseEntity.getBody());
    }

}

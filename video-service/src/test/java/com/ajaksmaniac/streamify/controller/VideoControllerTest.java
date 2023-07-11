package com.ajaksmaniac.streamify.controller;

import com.ajaksmaniac.streamify.dto.VideoDetailsDto;
import com.ajaksmaniac.streamify.repository.UserRepository;
import com.ajaksmaniac.streamify.repository.VideoRepository;
import com.ajaksmaniac.streamify.service.VideoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(VideoController.class)
public class VideoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    @Qualifier("videoServiceImplementation")
    private VideoService videoService;


    @MockBean
    private UserRepository userRepository;

    @MockBean
    private VideoRepository videoRepository;

    private final String VIDEO_NAME = "1-first_video";
    private final String USERNAME = "testUser";

    private final String CHANNEL = "testChanel";


    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testSaveVideo() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "1-first_video",
                MediaType.MULTIPART_FORM_DATA_VALUE, "test video data".getBytes(StandardCharsets.UTF_8));
        String url = "/video?name=" + VIDEO_NAME + "&channelName=" + CHANNEL;

        doNothing().when(videoService).saveVideo(any(), any(), any(), any(), any());
        mockMvc.perform(MockMvcRequestBuilders.multipart(url)
                        .file(file))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Video saved successfully."))
                .andDo(print());
    }

    @Test
    public void testGetVideoDetailsById() throws Exception {
        Long id = 1L;

        VideoDetailsDto videoDetailsDto = new VideoDetailsDto();
        videoDetailsDto.setId(id);
        videoDetailsDto.setName(VIDEO_NAME);
        videoDetailsDto.setChannelId(1L);
        videoDetailsDto.setUrl("/video/id/1");

        when(videoService.getVideoDetails(id)).thenReturn(videoDetailsDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/video/details/id/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(VIDEO_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$.channel").value(CHANNEL))
                .andExpect(MockMvcResultMatchers.jsonPath("$.url").value("/video/id/1"))
                .andDo(print());
    }

}


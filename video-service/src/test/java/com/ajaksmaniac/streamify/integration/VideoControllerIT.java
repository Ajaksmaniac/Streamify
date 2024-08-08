package com.ajaksmaniac.streamify.integration;

import com.ajaksmaniac.streamify.dto.VideoDetailsDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class VideoControllerIT extends BaseTest {

    private final MockMvc mockMvc;

    @Autowired
    public VideoControllerIT(MockMvc mockMvc){
        this.mockMvc = mockMvc;
    }

    @Test
    public void testSaveAndDeleteVideo() throws Exception {
        //Create new video
        MockMultipartFile file = new MockMultipartFile("file", "test.mp4", MediaType.MULTIPART_FORM_DATA_VALUE, "test content".getBytes());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/video")
                        .file(file)
                        .param("name", "Test Video")
                        .param("channelId", "1")
                        .param("description", "Test Description")
                        .header("x-auth-user-id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Video"))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andReturn();

        VideoDetailsDto createdVideo = toDto(result.getResponse().getContentAsString(), VideoDetailsDto.class);
        String expectedCreatedFilePath = "Files-Upload/"+createdVideo.getId()+"-Test Video";

        //Delete Video

        assertTrue(Files.exists(Path.of(expectedCreatedFilePath)));

        mockMvc.perform(MockMvcRequestBuilders.delete("/video/id/"+createdVideo.getId())
                        .header("x-auth-user-id", "1"))
                .andExpect(status().isOk())
                .andExpect(status().isOk());

        assertFalse(Files.exists(Path.of(expectedCreatedFilePath)));

    }

    @Test
    public void testGetVideoDetailsById() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.mp4", MediaType.MULTIPART_FORM_DATA_VALUE, "test content".getBytes());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/video")
                        .file(file)
                        .param("name", "Test Video")
                        .param("channelId", "1")
                        .param("description", "Test Description")
                        .header("x-auth-user-id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Video"))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andReturn();

        VideoDetailsDto createdVideo = toDto(result.getResponse().getContentAsString(), VideoDetailsDto.class);
        String expectedCreatedFilePath = "Files-Upload/"+createdVideo.getId()+"-Test Video";

        assertTrue(Files.exists(Path.of(expectedCreatedFilePath)));


        //Delete Video

        assertTrue(Files.exists(Path.of(expectedCreatedFilePath)));

        mockMvc.perform(MockMvcRequestBuilders.delete("/video/id/"+createdVideo.getId())
                        .header("x-auth-user-id", "1"))
                .andExpect(status().isOk())
                .andExpect(status().isOk());

        assertFalse(Files.exists(Path.of(expectedCreatedFilePath)));
    }

}


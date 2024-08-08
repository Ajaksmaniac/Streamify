package com.ajaksmaniac.streamify.integration;

import com.ajaksmaniac.streamify.dto.CommentDto;
import com.ajaksmaniac.streamify.dto.VideoDetailsDto;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CommentControllerIT extends BaseTest{

    private final MockMvc mockMvc;

    @Autowired
    public CommentControllerIT(MockMvc mockMvc){
        this.mockMvc = mockMvc;
    }

    @Test
    void testAddComment() throws Exception {
        // Add Video
        MockMultipartFile file = new MockMultipartFile("file", "test.mp4", org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE, "test content".getBytes());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/video")
                        .file(file)
                        .param("name", "Test Video")
                        .param("channelId", "1")
                        .param("description", "Test Description")
                        .header("x-auth-user-id", "1"))
                .andReturn();

        VideoDetailsDto createdVideo = toDto(result.getResponse().getContentAsString(), VideoDetailsDto.class);
        String expectedCreatedFilePath = "Files-Upload/"+createdVideo.getId()+"-Test Video";

        CommentDto newComment = new CommentDto();
        newComment.setContent("comment");
        newComment.setUserId(1L);
        newComment.setVideoId(createdVideo.getId());

        mockMvc.perform(MockMvcRequestBuilders.post("/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-auth-user-id",1)
                        .content(toJson(newComment)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        assertTrue(Files.exists(Path.of(expectedCreatedFilePath)));

        mockMvc.perform(MockMvcRequestBuilders.delete("/video/id/"+createdVideo.getId())
                        .header("x-auth-user-id", "1"))
                .andExpect(status().isOk())
                .andExpect(status().isOk());

        assertFalse(Files.exists(Path.of(expectedCreatedFilePath)));
    }

}

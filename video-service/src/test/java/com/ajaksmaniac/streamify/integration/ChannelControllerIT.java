package com.ajaksmaniac.streamify.integration;


import com.ajaksmaniac.streamify.dto.ChannelDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ChannelControllerIT extends BaseTest{


    private final MockMvc mockMvc;

    @Autowired
    public ChannelControllerIT(MockMvc mockMvc){
        this.mockMvc = mockMvc;
    }

    @Test
    void testGetChannelDetailsById_ifChannelExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/channel/id/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-auth-user-id",1))
                .andExpect(jsonPath("$.channelName", is("testChannel")))
                .andExpect(jsonPath("$.username", is("test1")));
    }

    @Test
    void testGetChannelDetailsById_ifChannelDoesntExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/channel/id/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-auth-user-id",1))
                .andExpect(status().isNotFound());
    }

    @Test
    void testSaveChannel_withValidUser() throws Exception {
        ChannelDto channelDto = new ChannelDto();
        channelDto.setChannelName("testChannel2");
        channelDto.setUsername("test1");

        mockMvc.perform(MockMvcRequestBuilders.post("/channel")
                .contentType(MediaType.APPLICATION_JSON)
                        .header("x-auth-user-id",1)
                        .content(toJson(channelDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.channelName", is("testChannel2")))
                .andExpect(jsonPath("$.username", is("test1")));
    }

    @Test
    void testSaveChannel_IfChannelAlreadyExists() throws Exception {
        ChannelDto channelDto = new ChannelDto();
        channelDto.setChannelName("testChannel");
        channelDto.setUsername("test1");

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8081/channel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-auth-user-id",1)
                        .content(toJson(channelDto)))
                .andExpect(status().isConflict());
    }

    @Test
    void testSaveChannel_withUserThatsNotContentCreator() throws Exception {
        ChannelDto channelDto = new ChannelDto();
        channelDto.setChannelName("testChannel3");
        channelDto.setUsername("test3");

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8081/channel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-auth-user-id",3)
                        .content(toJson(channelDto)))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    void testDeleteChannel_ifChannelExists() throws Exception {
        //Create a new Channel
        ChannelDto channelDto = new ChannelDto();
        channelDto.setChannelName("channelToDelete");
        channelDto.setUsername("test1");

        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8081/channel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-auth-user-id",1)
                        .content(toJson(channelDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.channelName", is("channelToDelete"))).andReturn();

        ChannelDto createdChannel = toDto(result.getResponse().getContentAsString(), ChannelDto.class);

        //Delete newly created channel
        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8081/channel/" + createdChannel.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-auth-user-id",1)
                        .content(toJson(channelDto)))
                .andExpect(status().isOk());

        //Assert if its deleted successfully
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8081/channel/id/" + createdChannel.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-auth-user-id",1))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteChannel_ifChannelDoesntExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8081/channel/id/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-auth-user-id",1))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateChannel() throws Exception {
        //Create a new Channel
        ChannelDto channelDto = new ChannelDto();
        channelDto.setChannelName("channelToUpdate");
        channelDto.setUsername("test1");

        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8081/channel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-auth-user-id",1)
                        .content(toJson(channelDto)))
                .andReturn();

        ChannelDto createdChannel = toDto(result.getResponse().getContentAsString(), ChannelDto.class);
        createdChannel.setChannelName("updatedChannel");
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8081/channel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-auth-user-id",1)
                        .content(toJson(createdChannel)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.channelName", is("updatedChannel"))).andReturn();
    }

    @Test
    void testSubscribe(){

    }

}

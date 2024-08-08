package com.ajaksmaniac.streamify.integration;

import com.ajaksmaniac.streamify.configuration.KafkaProducerService;
import com.ajaksmaniac.streamify.entity.ChannelEntity;
import com.ajaksmaniac.streamify.entity.RoleEntity;
import com.ajaksmaniac.streamify.entity.UserEntity;
import com.ajaksmaniac.streamify.repository.ChannelRepository;
import com.ajaksmaniac.streamify.repository.RoleRepository;
import com.ajaksmaniac.streamify.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import java.util.Collections;

@ImportAutoConfiguration(exclude = {KafkaAutoConfiguration.class})
public class BaseTest {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @MockBean
    private KafkaTemplate<String, String> kafkaTemplate;

    @MockBean
    KafkaProducerService kafkaProducerService;

    @PostConstruct
    public void setup() {
        //Persist roles
        RoleEntity adminRole = roleRepository.save(new RoleEntity(1L,"admin"));
        RoleEntity contentCreatorRole = roleRepository.save(new RoleEntity(2L,"content_creator"));
        RoleEntity userRole =  roleRepository.save(new RoleEntity(3L,"user"));

        //Persist test users
        UserEntity adminUser = new UserEntity();
        adminUser.setId(1L);
        adminUser.setUsername("test1");
        adminUser.setPassword("test");
        adminUser.setRole(adminRole);
        adminUser.setActive(true);
        adminUser.setSubscribedChannels(Collections.emptyList());
        userRepository.save(adminUser);

        UserEntity contentCreatorUser = new UserEntity();
        contentCreatorUser.setId(2L);
        contentCreatorUser.setUsername("test2");
        contentCreatorUser.setPassword("test");
        contentCreatorUser.setRole(contentCreatorRole);
        contentCreatorUser.setActive(true);
        contentCreatorUser.setSubscribedChannels(Collections.emptyList());
        userRepository.save(contentCreatorUser);

        UserEntity normalUser = new UserEntity();
        normalUser.setId(3L);
        normalUser.setUsername("test3");
        normalUser.setPassword("test");
        normalUser.setRole(userRole);
        normalUser.setActive(true);
        normalUser.setSubscribedChannels(Collections.emptyList());
        userRepository.save(normalUser);

        //Persist test channel

        ChannelEntity newChanel = new ChannelEntity();
        newChanel.setId(1L);
        newChanel.setChannelName("testChannel");
        newChanel.setUser(adminUser);
        newChanel.setVideos(Collections.emptyList());
        channelRepository.save(newChanel);

    }


    protected String toJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(object);
    }


    protected <T> T toDto(String json, Class<T> dtoClass) throws Exception {
        return new ObjectMapper().readValue(json, dtoClass);
    }

}

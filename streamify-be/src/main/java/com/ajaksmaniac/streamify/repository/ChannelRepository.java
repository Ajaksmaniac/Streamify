package com.ajaksmaniac.streamify.repository;

import com.ajaksmaniac.streamify.entity.ChannelEntity;
import com.ajaksmaniac.streamify.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChannelRepository extends JpaRepository<ChannelEntity, Long> {

    boolean existsByChannelName(String name);
    ChannelEntity findByChannelName(String name);
    void deleteChannelById(Long id);
    ChannelEntity getChannelById(Long id);

//    List<ChannelEntity> getAllChannels();

}

package com.ajaksmaniac.streamify.repository;

import com.ajaksmaniac.streamify.entity.ChannelEntity;
import com.ajaksmaniac.streamify.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChannelRepository extends JpaRepository<ChannelEntity, Long> {

    boolean existsByChannelName(String name);

    ChannelEntity getChannelById(Long id);

    List<ChannelEntity> findByUserId(Long id);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM ChannelEntity c WHERE c.id = :channelId AND c.user = :user")
    boolean isChannelOwnedByUser(@Param("channelId") Long channelId, @Param("user") UserEntity user);

}

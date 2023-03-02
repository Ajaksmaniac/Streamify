package com.ajaksmaniac.streamify.repository;

import com.ajaksmaniac.streamify.entity.ChannelEntity;
import com.ajaksmaniac.streamify.entity.UserEntity;
import com.ajaksmaniac.streamify.entity.VideoDetailsEntity;
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

    @Query(value = "SELECT c.channel_id, c.channel_name, c.user_id\n" +
            "FROM channel c\n" +
            "WHERE LOWER(c.channel_name) LIKE LOWER(concat('%', :keywords, '%'))\n" +
            "UNION\n" +
            "SELECT c.channel_id, c.channel_name, c.user_id\n" +
            "FROM user u\n" +
            "INNER JOIN channel c ON u.user_id = c.user_id\n" +
            "WHERE LOWER(u.username) LIKE LOWER(concat('%', :keywords, '%'));", nativeQuery = true)
    List<ChannelEntity> search(@Param("keywords") String keywords);
}

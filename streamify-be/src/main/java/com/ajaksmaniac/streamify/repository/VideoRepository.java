package com.ajaksmaniac.streamify.repository;

import com.ajaksmaniac.streamify.entity.UserEntity;
import com.ajaksmaniac.streamify.entity.VideoDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<VideoDetailsEntity,Long> {

    boolean existsByName(String name);

    boolean existsById(Long id );

    @Query(nativeQuery = true,value = "SELECT * FROM video_details")
    List<VideoDetailsEntity> getAllVideos();


    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END " +
            "FROM VideoDetailsEntity v " +
            "WHERE v.id = :videoId AND v.channel.user.id = :userId")
    boolean isVideoOwnedByUser(@Param("videoId") Long videoId,@Param("userId") Long userId);

    @Query(value = "SELECT v.video_id, v.name,v.channel_id,v.description, v.posted_at, v.video_url\n"+
    "FROM video_details v\n"+
    "WHERE LOWER(v.name) LIKE LOWER(concat('%', :keywords, '%'))\n"+
    "OR LOWER(v.description) LIKE LOWER(concat('%', :keywords, '%'))", nativeQuery = true)
    List<VideoDetailsEntity> search(@Param("keywords") String keywords);



}
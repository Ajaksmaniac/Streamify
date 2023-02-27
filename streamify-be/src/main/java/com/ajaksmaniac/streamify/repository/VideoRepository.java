package com.ajaksmaniac.streamify.repository;

import com.ajaksmaniac.streamify.entity.VideoDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<VideoDetailsEntity,Long> {

    VideoDetailsEntity findByName(String name);
    boolean existsByName(String name);
//    @Query(nativeQuery = true,value = "SELECT * FROM video_details v where v.id = :id")
//    VideoDetailsEntity findByVideoId(@Param("id") Long id);

    List<VideoDetailsEntity> findByChannelId(@Param("id") Long id);


    @Query(nativeQuery = true,value = "SELECT name FROM video")
    List<String> getAllEntryNames();

    @Query(nativeQuery = true,value = "SELECT * FROM video")
    List<VideoDetailsEntity> getAllVideos();


}

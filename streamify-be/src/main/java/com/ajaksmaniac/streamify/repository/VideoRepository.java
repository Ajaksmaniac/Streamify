package com.ajaksmaniac.streamify.repository;

import com.ajaksmaniac.streamify.entity.UserEntity;
import com.ajaksmaniac.streamify.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<VideoEntity,Long> {

    VideoEntity findByName(String name);

    boolean existsByName(String name);
    @Query(nativeQuery = true,value = "SELECT * FROM video v where v.id = :id")
    VideoEntity findByVideoId(@Param("id") Long id);
    @Query(nativeQuery = true,value = "SELECT name FROM video")
    List<String> getAllEntryNames();
}

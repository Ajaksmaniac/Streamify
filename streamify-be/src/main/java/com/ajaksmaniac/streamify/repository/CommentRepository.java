package com.ajaksmaniac.streamify.repository;

import com.ajaksmaniac.streamify.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Long, CommentEntity> {
    CommentEntity findById(Long id);
    @Query("SELECT * FROM comment where video_id = :id")
    List<CommentEntity> findByMovieId(@Param("id") Long id);

}

package com.ajaksmaniac.streamify.repository;

import com.ajaksmaniac.streamify.entity.CommentEntity;
import com.ajaksmaniac.streamify.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    @Query("SELECT * FROM comment where id = :id")
    CommentEntity findByCommentId(@Param("id")Long id);
    @Query("SELECT * FROM comment where video_id = :id")
    List<CommentEntity> findByMovieId(@Param("id") Long id);

    boolean existsById(Long name);

}

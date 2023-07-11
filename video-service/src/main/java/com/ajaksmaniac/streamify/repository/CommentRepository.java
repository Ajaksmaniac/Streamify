package com.ajaksmaniac.streamify.repository;

import com.ajaksmaniac.streamify.entity.CommentEntity;
import com.ajaksmaniac.streamify.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    @Query(value = "SELECT * FROM comment c where c.id = :id", nativeQuery = true)
    Optional<CommentEntity> findByCommentId(@Param("id")Long id);
    @Query(value = "SELECT * FROM comment c where c.video_id = :id", nativeQuery = true)
    List<CommentEntity> findByMovieId(@Param("id") Long id);

    boolean existsById(Long name);

}

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
    @Query(value = "SELECT * FROM comment c where c.id = :id", nativeQuery = true)
    CommentEntity findByCommentId(@Param("id")Long id);
    @Query(value = "SELECT * FROM comment c where c.video_id = :id", nativeQuery = true)
    List<CommentEntity> findByMovieId(@Param("id") Long id);

    boolean existsById(Long name);

    void deleteCommendById(Long id);

//    @Query(value = "DELETE FROM comment WHERE video_id = :id")
    void deleteAllCommentsByVideoDetailsId(Long id);

}

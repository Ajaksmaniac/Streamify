package com.ajaksmaniac.streamify.repository;

import com.ajaksmaniac.streamify.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
    @Query(nativeQuery = true,value = "SELECT * FROM user u WHERE u.user_id = :id")
    UserEntity findByUserId(@Param("id") Long id);

    @Query(nativeQuery = true,value = "SELECT username FROM user")
    List<String> getAllUsernames();

}

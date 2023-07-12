package com.ajaksmaniac.streamify.notifications.repository;

import com.ajaksmaniac.streamify.notifications.entity.UserEntity;
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
    @Query(value = "SELECT u FROM UserEntity u  WHERE u.id = :id")
    UserEntity findByUserId(@Param("id") Long id);

}

package com.ajaksmaniac.streamify.repository;

import com.ajaksmaniac.streamify.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    boolean existsByName(String name);
    RoleEntity findByName(String name);

}

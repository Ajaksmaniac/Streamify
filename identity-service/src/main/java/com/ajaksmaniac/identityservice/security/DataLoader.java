package com.ajaksmaniac.identityservice.security;

import com.ajaksmaniac.identityservice.entity.RoleEntity;
import com.ajaksmaniac.identityservice.entity.UserEntity;
import com.ajaksmaniac.identityservice.repository.RoleRepository;
import com.ajaksmaniac.identityservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Initialize roles
        if (!roleRepository.existsById(1L)) {
            roleRepository.save(new RoleEntity(1L, "user"));
        }
        if (!roleRepository.existsById(2L)) {
            roleRepository.save(new RoleEntity(2L, "content_creator"));
        }
        if (!roleRepository.existsById(3L)) {
            roleRepository.save(new RoleEntity(3L, "admin"));
        }

        // Initialize users
        if (!userRepository.existsByUsername("testcafeUser")) {
            UserEntity user = new UserEntity("testcafeUser", "$2a$10$/Ewh3D0jDhgtzJvNHUqB6OPjeSxkyqOmaOFkD2Q9GyQAtPeThfJfG");
            user.setActive(true);
            user.setRole(roleRepository.findById(2L).orElseThrow());
            userRepository.save(user);
        }
    }
}

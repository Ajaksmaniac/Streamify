package com.ajaksmaniac.streamify.controller;

import com.ajaksmaniac.streamify.dto.UserDto;
import com.ajaksmaniac.streamify.entity.UserEntity;
import com.ajaksmaniac.streamify.exception.user.UserNotExistentException;
import com.ajaksmaniac.streamify.repository.UserRepository;
import com.ajaksmaniac.streamify.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
@Slf4j
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    UserDetailsManager userDetailsManager;

    @PostMapping("/{id}/changePassword")
    @PreAuthorize("#username == authentication.principal.username || authentication.principal.role.name == 'admin'")
    public ResponseEntity<String> changePassword(@RequestParam("username") String username,
                                                 @RequestParam("newPassword") String newPassword) {
        log.info("Token : {}", SecurityContextHolder.getContext().getAuthentication());
        UserDetails userEntity = userDetailsManager.loadUserByUsername(username);

        ((UserEntity) userEntity).setPassword(newPassword);

        userDetailsManager.updateUser(userEntity);

        return ResponseEntity.ok(String.format("Password changed for user with id %d ",((UserEntity) userEntity).getId()));


    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUsernameById(@PathVariable("id") Long id){


       return ResponseEntity.ok(userService.getUserById(id));
    }
}

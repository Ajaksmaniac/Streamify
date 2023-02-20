package com.ajaksmaniac.streamify.controller;

import com.ajaksmaniac.streamify.dto.SignupDto;
import com.ajaksmaniac.streamify.dto.LoginDto;
import com.ajaksmaniac.streamify.entity.UserEntity;
import com.ajaksmaniac.streamify.security.TokenGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Autowired
    UserDetailsManager userDetailsManager;
    @Autowired
    TokenGenerator tokenGenerator;
    @Autowired
    DaoAuthenticationProvider daoAuthenticationProvider;
    @Autowired
    @Qualifier("jwtRefreshTokenAuthProvider")
    JwtAuthenticationProvider jwtRefreshTokenAuthProvider;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody SignupDto signupDTO){

        UserEntity user = new UserEntity(signupDTO.getUsername(), signupDTO.getPassword());
        log.info(user.toString( ));

        if(userDetailsManager.userExists(user.getUsername())){
            log.info(user.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
        }
        userDetailsManager.createUser(user);
        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(user,signupDTO.getPassword(), Collections.EMPTY_LIST);

        return ResponseEntity.ok(tokenGenerator.createToken(authentication));

    }


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDto loginDTO){
        log.info(String.valueOf(loginDTO));

        Authentication authentication = daoAuthenticationProvider.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(loginDTO.getUsername(),loginDTO.getPassword()));
        return ResponseEntity.ok(tokenGenerator.createToken(authentication));
    }

}

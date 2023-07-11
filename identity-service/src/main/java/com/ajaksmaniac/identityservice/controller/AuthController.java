package com.ajaksmaniac.identityservice.controller;

import com.ajaksmaniac.identityservice.dto.LoginDto;
import com.ajaksmaniac.identityservice.dto.SignupDto;
import com.ajaksmaniac.identityservice.entity.UserEntity;
import com.ajaksmaniac.identityservice.security.TokenGenerator;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.QueryParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Slf4j
//@CrossOrigin
public class AuthController {

    @Autowired
    UserDetailsManager userDetailsManager;
    @Autowired
    TokenGenerator tokenGenerator;
    @Autowired
    DaoAuthenticationProvider daoAuthenticationProvider;
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody SignupDto signupDTO) {

        UserEntity user = new UserEntity(signupDTO.getUsername(), signupDTO.getPassword());

        userDetailsManager.createUser(user);
        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(user, signupDTO.getPassword(), Collections.EMPTY_LIST);
        return ResponseEntity.ok(tokenGenerator.createToken(authentication));

    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDto loginDTO) {
        log.info("LOGGED IN FROM IDENTITY SERVICE");
        Authentication authentication = daoAuthenticationProvider.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(loginDTO.getUsername(), loginDTO.getPassword()));
        return ResponseEntity.ok(tokenGenerator.createToken(authentication));
    }

    @PostMapping("/validateToken")
    public ResponseEntity<Long> validate() {
//        daoAuthenticationProvider.
        UserEntity principal = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("VALIDATETION "+ principal);

        if(principal.getId() != null){
            return ResponseEntity.ok(principal.getId());
        }
        return ResponseEntity.ok(null);
    }


}

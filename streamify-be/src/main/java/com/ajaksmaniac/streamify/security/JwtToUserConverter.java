package com.ajaksmaniac.streamify.security;

import com.ajaksmaniac.streamify.entity.UserEntity;
import com.ajaksmaniac.streamify.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import java.util.Collections;

@Component
@Slf4j
public class JwtToUserConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

    @Autowired
    UserRepository userRepository;
    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt jwt){

        UserEntity user = userRepository.findByUserId(Long.valueOf(jwt.getSubject()));
        return new UsernamePasswordAuthenticationToken(user,jwt, Collections.emptyList());
    }
}

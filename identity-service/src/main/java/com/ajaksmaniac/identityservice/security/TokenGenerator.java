package com.ajaksmaniac.identityservice.security;

import com.ajaksmaniac.identityservice.dto.TokenDto;
import com.ajaksmaniac.identityservice.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class TokenGenerator {

    @Autowired
    JwtEncoder accessTokenEncoder;

    @Autowired
    @Qualifier("jwtRefreshTokenEncoder")
    JwtEncoder refreshTokenEncoder;

    private String createAccessToken(Authentication authentication){
        UserEntity user = (UserEntity) authentication.getPrincipal();
        Instant now =Instant.now();
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("myApp")
                .issuedAt(now)
                .subject(user.getIdString())
                .build();

        return accessTokenEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

    private String createRefreshToken(Authentication authentication){
        UserEntity user = (UserEntity) authentication.getPrincipal();
        Instant now =Instant.now();
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("myApp")
                .issuedAt(now)
                .expiresAt(now.plus(30, ChronoUnit.DAYS))
                .subject(user.getIdString())
                .build();

        return refreshTokenEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

    public TokenDto createToken(Authentication authentication){
        if(! (authentication.getPrincipal() instanceof UserEntity user)){
            throw new BadCredentialsException(
                    MessageFormat.format("principal {0} is not of User types", authentication.getPrincipal().getClass())
            );
        }
        TokenDto tokenDTO = new TokenDto();
        tokenDTO.setUserId(user.getIdString());
        tokenDTO.setAccessToken(createAccessToken(authentication));

        String refreshToken;
        if(authentication.getCredentials() instanceof Jwt jwt){
            Instant now =Instant.now();
            Instant expiresAt = jwt.getExpiresAt();
            Duration duration = Duration.between(now,expiresAt);
            long daysUntilExpired = duration.toDays();
            if(daysUntilExpired < 7) {
                refreshToken = createRefreshToken(authentication);
            }else{
                refreshToken = jwt.getTokenValue();
            }
        } else {
            refreshToken = createRefreshToken(authentication);
        }
        tokenDTO.setRefreshToken(refreshToken);
        return tokenDTO;
    }

}

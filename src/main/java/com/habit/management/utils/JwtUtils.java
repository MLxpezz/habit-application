package com.habit.management.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${jwt.expiration.time}")
    private String expirationTime;

    private SecretKey key = Jwts.SIG.HS256.key().build();

    public String createToken(Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        assert userDetails != null;
        String email = userDetails.getUsername();

        Instant currentDate = Instant.now();
        Instant expirationDate = currentDate.plus(Long.parseLong(expirationTime), ChronoUnit.MILLIS);

        String roles = userDetails.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts
                .builder()
                .signWith(key)
                .subject(email)
                .claim("authorities", roles)
                .issuedAt(Date.from(currentDate))
                .expiration(Date.from(expirationDate))
                .compact();
    }

    public boolean isValidToken(String token) {
        try {
            Jwts.
                    parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return true;
        }
        catch (JwtException exception) {
            return false;
        }
    }

    public String getEmailFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

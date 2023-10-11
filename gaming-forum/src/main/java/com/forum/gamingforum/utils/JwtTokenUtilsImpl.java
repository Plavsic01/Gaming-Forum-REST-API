package com.forum.gamingforum.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenUtilsImpl implements TokenUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${token.expiration}")
    private Long expiration;


    @Bean
    public Key getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Claims getClaims(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();

        } catch (Exception e) {
        }

        return claims;
    }

    public boolean isExpired(String token){
        try{
            return getClaims(token).getExpiration().before(new Date(System.currentTimeMillis()));
        }catch (Exception e){

        }
        return true;
    }

    public String getUsername(String token) {
        String username = null;
        try {
            return getClaims(token).getSubject();
        } catch (Exception e) {
        }
        return username;
    }

    public boolean validateToken(String token, UserDetails userDetails){
        String username = getUsername(token);
        return username.equals(userDetails.getUsername()) && !isExpired(token);
    }

}

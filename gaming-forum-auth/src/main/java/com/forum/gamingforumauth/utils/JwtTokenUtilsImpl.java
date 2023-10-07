package com.forum.gamingforumauth.utils;


import com.forum.gamingforumauth.model.User;
import com.forum.gamingforumauth.repository.UserRepository;
import com.forum.gamingforumauth.service.user.UserServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtilsImpl implements TokenUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${token.expiration}")
    private Long expiration;

    @Autowired
    private UserRepository userRepository;

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

    public String generateToken(UserDetails userDetails){
        User user = userRepository.findByUsername(userDetails.getUsername()).orElse(null);
        if(user != null){
            Map<String,Object> claims = new HashMap<>();
            claims.put("id",user.getId());
            claims.put("sub",userDetails.getUsername());
            claims.put("created",new Date(System.currentTimeMillis()));
            claims.put("roles",userDetails.getAuthorities());

            return Jwts.builder().setClaims(claims).setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                    .signWith(getKey()).compact();
        }
        return null;

    }

}

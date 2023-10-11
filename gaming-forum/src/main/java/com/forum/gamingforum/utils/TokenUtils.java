package com.forum.gamingforum.utils;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;

public interface TokenUtils {
    Key getKey();
    Claims getClaims(String token);
    boolean isExpired(String token);
    String getUsername(String token);
    boolean validateToken(String token, UserDetails userDetails);
//    String generateToken(UserDetails userDetails);
}

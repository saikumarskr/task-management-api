package com.taskmanagement.task_management_api.Util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private  final long EXPIRATION =1000*60*60;
    private final String SECRET ="MY-NAME-IS-ErenYager-I-AM-AN-JAVA-BACKEND-DEVELOPER";
    private final SecretKey key= Keys.hmacShaKeyFor(SECRET.getBytes());
    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    public Claims extractClaims (String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername (String token){
        return extractClaims(token)
                .getSubject();
    }
    public boolean validateToken (String username, UserDetails userDetails, String token){
        return username.equals(userDetails.getUsername()) && !(isTokenExpired(token));
    }
    public boolean isTokenExpired(String  token){
        return extractClaims(token).getExpiration().before(new Date());
    }
}

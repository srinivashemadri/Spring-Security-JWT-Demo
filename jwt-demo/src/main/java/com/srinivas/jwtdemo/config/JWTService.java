package com.srinivas.jwtdemo.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    private static final String SECRET_KEY = "wluzLE8T2RXvKsERX96rxd7IitmIBqlS2lpUS/Ptpyn9fG6qfixizjlPcUg9Yd7V7pqvCPJwpyuUUtWLHfXGiExbJltMoeTF+IyP91G5sWlpuTvTdsv60FhbS2T08yM7LYUmSvRudHt3SQlotW1fnyrZ7fdiq6cyiJdBvS3Q/cCxaM1VSEKOZYAbfk5JgJQ6vbQ3suirM9cXT813U7nyaUB74T8v7aBAFU3s9jgMscalpAi8+YFjIfClGhnzk5YYqRLCtgrnlSS6R/Tgk5RO3jYlFPsinuk9m1M7LAkJSM6n6dknEqeVQJCnGiePcWj39vXQOCjP5dmP03ILFmiMSZqOiCr4+IK5PKJfMw7il/s=\n";



    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);

    }
    public String extractUserName(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){
        long currentMilliSeconds = System.currentTimeMillis();
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(currentMilliSeconds))
                .setExpiration(new Date(currentMilliSeconds + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String userName = extractUserName(token);
        return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }

    private Date extractExpirationDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJwt(token).getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

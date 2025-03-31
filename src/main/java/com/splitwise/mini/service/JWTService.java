package com.splitwise.mini.service;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


//Service class for generating, validating, and extracting information from JWT tokens.
@Service
public class JWTService {

    private String secretKey = "";

    // Constructor that generates a random secret key for JWT signing.
    public JWTService() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
        SecretKey sk = keyGen.generateKey();
        secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
    }

    // Generates a JWT token for the given email with a validity of 24 hours.
   
    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims().add(claims)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)) // 24-hour validity
                .and()
                .signWith(getKey())
                .compact();
    }

    //Retrieves the secret key for JWT signing.
    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //Extracts the username from a JWT token.
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    //Extracts a specific claim from the JWT token.
    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    //Extracts all claims from the JWT token.
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Validates the JWT token against the provided user details.
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    //Checks if the JWT token has expired.   
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //Extracts the expiration date from the JWT token.   
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}

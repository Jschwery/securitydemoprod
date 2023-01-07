package com.jschwery.securitydemo.services;

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
public class JwtService {

    private static final String SECRET_KEY = "71337336763979244226452948404D635166546A576E5A7234753778217A2543";


    public <T> T extractClaimToken(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractClaims(token);
        return claimsResolver.apply(claims);
    }

    public String findUsername(String authToken){
        return extractClaimToken(authToken, Claims::getSubject);
    }

    public String generateToken(UserDetails details){
        return generateToken(new HashMap<>(), details);
    }

    public boolean checkTokenValidity(String authToken, UserDetails userDetails){
        final String username = findUsername(authToken);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(authToken);
    }

    private boolean isTokenExpired(String authToken) {
        return findExpiration(authToken).before(new Date());
    }

    private Date findExpiration(String authToken) {
        return extractClaimToken(authToken, Claims::getExpiration);
    }

    public String generateToken(Map<String, Object> claims, UserDetails details){
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(details.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24 * 365))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    /**
     *
     * @param token the jwt token that has been passed in to extract the claims from
     * @return return
     */
    private Claims extractClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}

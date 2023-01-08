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

    /**
     *
     * @param token JWT token to find the claims from
     * @param claimsResolver a Function that takes in Claims as input
     * @param <T>
     * @return
     */
    public <T> T extractClaimToken(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractClaims(token);//get claims from the token
        return claimsResolver.apply(claims);//apply a function that takes in a claim and returns any type for flexibility
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

    /**
     *
     * @param authToken takes in the token to check if it has expired or not
     * @return returns true if the token is expired, false otherwise
     */
    private boolean isTokenExpired(String authToken) {
        return findExpiration(authToken).before(new Date());
    }

    /**
     *
     * @param authToken takes in the jwt token string and returns the expiration date of said token.
     * @return Date of token expiration
     */
    private Date findExpiration(String authToken) {
        return extractClaimToken(authToken, Claims::getExpiration);
    }

    /**
     *
     * @param claims claims to be added to the token implemented using a map,
     *               where String is the json key, and the value is an object for flexibility for representing any value type.
     * @param details User details representing the details of the user that include the username and credentials
     *                of the user to be added to the token for authentication, and authorization.
     *
     * @return the encoded JWT, encoded with HMAC-SHA256 algorithm
     */
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
     * @return returns claims that describe the subject, such as the user from the body
     */
    private Claims extractClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     *
     * @return returns a secret key that can be used for signing the JWT token
     */
    private Key getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}

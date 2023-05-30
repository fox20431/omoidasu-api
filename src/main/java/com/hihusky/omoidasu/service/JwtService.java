package com.hihusky.omoidasu.service;

import com.hihusky.omoidasu.entity.User;
import com.hihusky.omoidasu.repo.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class JwtService {

    @Value("${omoidasu.security.jwt.encryption-key}")
    private String secretKey;
    @Value("${omoidasu.security.jwt.access-token.expiration-in-milliseconds}")
    private long accessTokenExpiration;
    @Value("${omoidasu.security.jwt.refresh-token.expiration-in-milliseconds}")
    private long refreshTokenExpiration;

    private final UserRepository userRepository;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private  <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public String generateAccessToken(UserDetails userDetails) {
        List<String> authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        Map<String, Object> authoritiesClaims = Map.of("authorities", authorities);
        return Jwts
                .builder()
                .setSubject(userDetails.getUsername())
                .setClaims(authoritiesClaims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return Jwts
                .builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public User getUserFromRefreshToken(String refreshToken) {
        var claims = extractAllClaims(refreshToken);
        var username = claims.getSubject();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return user;
    }
}

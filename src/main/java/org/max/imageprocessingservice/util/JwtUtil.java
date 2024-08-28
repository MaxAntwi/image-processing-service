package org.max.imageprocessingservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.max.imageprocessingservice.entity.User;
import org.max.imageprocessingservice.exception.InvalidTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtUtil {

    @Value("${JWT_SECRET}")
    public String secret;

    private final ObjectMapper objectMapper;

    public String generateToken(User user) {
        Map<String, String> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("username", user.getUsername());
        claims.put("role", String.valueOf(user.getRole()));
        return createToken(claims);
    }

    private String createToken(Map<String, String> claims) {
        return Jwts.builder()
                .claims(claims)
                .subject("User Details")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey () {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        String[] chunks = token.split("\\.");
        if (chunks.length < 2) {
            throw new InvalidTokenException("Token does not contain a valid payload", HttpStatus.FORBIDDEN.value());
        }
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        try{
            JsonNode jsonNode = objectMapper.readTree(payload);
            JsonNode username = jsonNode.get("username");
            if (username == null) {
                throw new InvalidTokenException("Email not found in token payload",  HttpStatus.FORBIDDEN.value());
            }
            return username.asText();
        } catch (JsonProcessingException e) {
            log.error("Error parsing token", e);
            throw new InvalidTokenException("Invalid Token",  HttpStatus.FORBIDDEN.value());
        }
    }

    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

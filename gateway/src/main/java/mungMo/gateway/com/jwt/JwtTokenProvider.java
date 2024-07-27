package mungMo.gateway.com.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.http.server.reactive.ServerHttpRequest;

import javax.crypto.SecretKey;
import java.util.Optional;

@Component
public class JwtTokenProvider {

    private final SecretKey key;
    public static final String AUTHORIZATION_HEADER = "Authorization";

    public JwtTokenProvider(@Value("${jwt.secret-key}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractSubject(String accessToken) {
        Claims claims = parseClaims(accessToken);
        return claims.getSubject();
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public String getAccessToken(ServerHttpRequest request) {
            System.out.println("gateway JwtTokenProvider authorization = " + request.getHeaders().getFirst(AUTHORIZATION_HEADER));

            return Optional.ofNullable(request.getHeaders().getFirst(AUTHORIZATION_HEADER))
                    .filter(val -> val.startsWith("Bearer "))
                    .map(token -> token.substring(7))
                    .orElseThrow();
    }
}
package mungMo.memberService.domain.member.oauth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class JwtTokenProvider {

    private final Key key;  // test 용
    public static final String AUTHORIZATION_HEADER = "Authorization"; //헤더 이름

    public JwtTokenProvider(@Value("${jwt.secret-key}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }
    // jwt 생성
    public String generate(String subject, Date expiredAt) {
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(expiredAt)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }
    //토큰 유효성 검증 메서드
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

    // HttpServletRequest에서 Authorization Header를 통해 access token을 추출하는 메서드입니다.
    public String getAccessToken(HttpServletRequest request) {
        try {
            return Optional.ofNullable(request.getHeader(AUTHORIZATION_HEADER))
                    .filter(val -> val.startsWith("Bearer "))
                    .get()
                    .substring(7);
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
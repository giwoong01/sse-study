package com.sse.sse_study_backend.global.jwt;

import com.sse.sse_study_backend.global.jwt.dto.TokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class TokenProvider {

    @Value("${token.expire.time.access}")
    private String accessTokenExpireTime;

    @Value("${jwt.secret}")
    private String secret;

    private Key key;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64URL.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (UnsupportedJwtException | MalformedJwtException exception) {
            log.error("JWT is not valid");
        } catch (SignatureException exception) {
            log.error("JWT signature validation fails");
        } catch (ExpiredJwtException exception) {
            log.error("JWT expired");
        } catch (IllegalArgumentException exception) {
            log.error("JWT is null or empty or only whitespace");
        } catch (Exception exception) {
            log.error("JWT validation fails", exception);
        }

        return false;
    }

    public TokenDto generateToken(String memberId) {
        String accessToken = generateAccessToken(memberId);

        return TokenDto.from(accessToken);
    }

    private String generateAccessToken(String memberId) {
        Date date = new Date();
        Date accessExpiryDate = new Date(date.getTime() + Long.parseLong(accessTokenExpireTime));

        return Jwts.builder()
                .setSubject(memberId)
                .setIssuedAt(date)
                .setExpiration(accessExpiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Long getMemberIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return Long.valueOf(claims.getSubject());
    }

}

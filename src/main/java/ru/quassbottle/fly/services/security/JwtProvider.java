package ru.quassbottle.fly.services.business;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.quassbottle.fly.entities.Account;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@Slf4j
public class JwtProvider {
    private final SecretKey refreshSecret;
    private final SecretKey accessSecret;
    private final int accessExpirationInMinutes;
    private final int refreshExpirationInDays;

    public JwtProvider(@Value("${application.jwt.access.secret}") String accessSecretKey,
                       @Value("${application.jwt.refresh.secret}") String refreshSecretKey,
                       @Value("${application.jwt.access.expirationInMinutes}") int tokenExpirationInMinutes,
                       @Value("${application.jwt.refresh.expirationInDays}") int refreshExpirationInDays) {
        this.refreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshSecretKey));
        this.accessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessSecretKey));
        this.accessExpirationInMinutes = tokenExpirationInMinutes;
        this.refreshExpirationInDays = refreshExpirationInDays;
    }

    public String generateAccessToken(@NonNull Authentication authentication) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusMinutes(accessExpirationInMinutes).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .setExpiration(accessExpiration)
                .signWith(this.accessSecret)
                .claim("roles", authentication.getAuthorities())
                .compact();
    }

    public String generateRefreshToken(@NonNull Authentication authentication) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusDays(refreshExpirationInDays).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .setExpiration(accessExpiration)
                .signWith(this.refreshSecret)
                .compact();
    }

    public boolean validateAccessToken(@NonNull String accessToken) {
        return validateToken(accessToken, this.accessSecret);
    }

    public boolean validateRefreshToken(@NonNull String refreshToken) {
        return validateToken(refreshToken, this.refreshSecret);
    }

    private boolean validateToken(@NonNull String token, @NonNull Key secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt", mjEx);
        } catch (SignatureException sEx) {
            log.error("Invalid signature", sEx);
        } catch (Exception e) {
            log.error("Invalid token", e);
        }
        return false;
    }

    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token, this.accessSecret);
    }

    public Claims getRefreshClaims(@NonNull String token) {
        return getClaims(token, this.refreshSecret);
    }

    private Claims getClaims(@NonNull String token, @NonNull Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

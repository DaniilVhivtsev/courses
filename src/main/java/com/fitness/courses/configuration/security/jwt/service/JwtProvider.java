package com.fitness.courses.configuration.security.jwt.service;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fitness.courses.http.user.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider
{
    private static final @NotNull Logger LOG = LoggerFactory.getLogger(JwtProvider.class);
    private static final @NotNull ZoneId MOSCOW_ZONE_ID = ZoneId.of("Europe/Moscow");

    private final SecretKey jwtAccessSecret;
    private final SecretKey jwtRefreshSecret;
    private final Duration jwtAccessLifetime;
    private final Duration jwtRefreshLifetime;

    public JwtProvider(
            @Value("${jwt.secret.access.key}") String jwtAccessSecret,
            @Value("${jwt.secret.refresh.key}") String jwtRefreshSecret,
            @Value("${jwt.secret.access.lifetime}") Duration jwtAccessLifetime,
            @Value("${jwt.secret.refresh.lifetime}") Duration jwtRefreshLifetime)
    {
        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
        this.jwtRefreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret));

        this.jwtAccessLifetime = jwtAccessLifetime;
        this.jwtRefreshLifetime = jwtRefreshLifetime;
    }

    /**
     * Сгенерировать Access token для переданного пользователя.
     *
     * @param user пользователь.
     * @return access token.
     */
    public @NotNull String generateAccessToken(@NotNull User user)
    {
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusSeconds(jwtAccessLifetime.getSeconds())
                .atZone(MOSCOW_ZONE_ID)
                .toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(accessExpiration)
                .signWith(jwtAccessSecret)
                .claim("roles", user.getRoles())// можно добавить id
                .compact();
    }

    /**
     * Сгенерировать Refresh token для переданного пользователя.
     *
     * @param user пользователь.
     * @return refresh token.
     */
    public @NotNull String generateRefreshToken(@NotNull User user)
    {
        final LocalDateTime now = LocalDateTime.now();
        final Instant refreshExpirationInstant = now.plusSeconds(jwtRefreshLifetime.getSeconds())
                .atZone(MOSCOW_ZONE_ID)
                .toInstant();
        final Date refreshExpiration = Date.from(refreshExpirationInstant);
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(refreshExpiration)
                .signWith(jwtRefreshSecret)
                .compact();
    }

    /**
     * Проверить на валидность переданный access token.
     *
     * @param accessToken access token.
     * @return true - если переданный токен валидный, иначе false.
     */
    public boolean validateAccessToken(@NotNull String accessToken)
    {
        return validateToken(accessToken, jwtAccessSecret);
    }

    /**
     * Проверить на валидность переданный refresh token.
     *
     * @param refreshToken refresh token.
     * @return true - если переданный токен валидный, иначе false.
     */
    public boolean validateRefreshToken(@NotNull String refreshToken)
    {
        return validateToken(refreshToken, jwtRefreshSecret);
    }

    private boolean validateToken(@NotNull String token, @NotNull Key secret)
    {
        try
        {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        }
        catch (ExpiredJwtException e)
        {
            final String message = "Token expired.";
            LOG.warn(message, e);
        }
        catch (UnsupportedJwtException e)
        {
            final String message = "Unsupported jwt.";
            LOG.warn(message, e);
        }
        catch (MalformedJwtException e)
        {
            final String message = "Malformed jwt.";
            LOG.warn(message, e);
        }
        catch (SignatureException e)
        {
            final String message = "Invalid signature.";
            LOG.warn(message, e);
        }
        catch (IllegalArgumentException e)
        {
            final String message = "Invalid token.";
            LOG.warn(message, e);
        }

        return false;
    }

    /**
     * Получить полезную нагрузку из переданного access токена.
     *
     * @param accessToken access token.
     * @return полезная нагрузка.
     */
    public @NotNull Claims getAccessClaims(@NotNull String accessToken)
    {
        return getClaims(accessToken, jwtAccessSecret);
    }

    /**
     * Получить полезную нагрузку из переданного refresh токена.
     *
     * @param refreshToken refresh token.
     * @return полезная нагрузка.
     */
    public @NotNull Claims getRefreshClaims(@NotNull String refreshToken)
    {
        return getClaims(refreshToken, jwtRefreshSecret);
    }

    private Claims getClaims(@NotNull String token, @NotNull Key secret)
    {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

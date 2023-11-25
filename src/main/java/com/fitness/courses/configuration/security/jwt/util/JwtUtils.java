package com.fitness.courses.configuration.security.jwt.util;

import static com.fitness.courses.configuration.security.jwt.util.JwtConstants.CLAIM_USER_ROLES_KEY;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.fitness.courses.http.user.model.Role;

import io.jsonwebtoken.Claims;

/**
 * Утилитарный класс для работы с JWT.
 */
public final class JwtUtils
{
    private JwtUtils()
    {
    }

    /**
     * Создать объект {@link UsernamePasswordAuthenticationToken} по переданным полезным данным JWT.
     *
     * @param claims полезные данные JWT.
     * @return объект {@link UsernamePasswordAuthenticationToken}.
     */
    public static @NotNull UsernamePasswordAuthenticationToken generate(@NotNull Claims claims)
    {
        return new UsernamePasswordAuthenticationToken(claims.getSubject(), null, getRoles(claims));
    }

    /**
     * Получить username из полезных данных JWT.
     *
     * @param claims полезные данные JWT.
     * @return username из полезных данных JWT (или null если его нет в claims).
     */
    public static @Null String getUsername(@NotNull Claims claims)
    {
        return claims.getSubject();
    }

    /**
     * Получить роли пользователя из полезных данных JWT.
     *
     * @param claims полезные данные JWT.
     * @return коллекция ролей пользователя из полезных данных JWT (или null если их нет в claims).
     */
    @SuppressWarnings("unchecked")
    private static @Null Set<Role> getRoles(Claims claims)
    {
        if (!claims.containsKey(CLAIM_USER_ROLES_KEY))
        {
            return null;
        }
        final List<String> roles = claims.get(CLAIM_USER_ROLES_KEY, List.class);
        return roles.stream()
                .map(Role::valueOf)
                .collect(Collectors.toSet());
    }
}

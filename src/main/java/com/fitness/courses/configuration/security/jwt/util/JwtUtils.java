package com.fitness.courses.configuration.security.jwt.util;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.fitness.courses.http.user.model.Role;

import io.jsonwebtoken.Claims;

public final class JwtUtils
{
    private static final String CLAIMS_ROLE_KEY = "roles";

    private JwtUtils()
    {
    }

    public static UsernamePasswordAuthenticationToken generate(Claims claims)
    {
        return new UsernamePasswordAuthenticationToken(claims.getSubject(), null, getRoles(claims));
    }

    public static String getUsername(Claims claims)
    {
        return claims.getSubject();
    }

    private static Set<Role> getRoles(Claims claims)
    {
        final List<String> roles = claims.get(CLAIMS_ROLE_KEY, List.class);
        return roles.stream()
                .map(Role::valueOf)
                .collect(Collectors.toSet());
    }
}

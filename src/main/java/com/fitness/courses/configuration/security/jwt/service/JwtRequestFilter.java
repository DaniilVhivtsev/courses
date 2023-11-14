package com.fitness.courses.configuration.security.jwt.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fitness.courses.configuration.security.jwt.util.JwtUtils;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter
{
    private static final String AUTHORIZATION = "Authorization";
    private static final String JWT_PREFIX = "Bearer ";

    private final JwtProvider jwtProvider;

    public JwtRequestFilter(JwtProvider jwtProvider)
    {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        final Optional<String> tokenOptional = getTokenFromRequest(request);

        if (tokenOptional.isPresent() && jwtProvider.validateAccessToken(tokenOptional.get()))
        {
            final Claims claims = jwtProvider.getAccessClaims(tokenOptional.get());
            final UsernamePasswordAuthenticationToken jwtInfo = JwtUtils.generate(claims);
//            jwtInfo.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(jwtInfo);
        }

        filterChain.doFilter(request, response);
    }

    private static Optional<String> getTokenFromRequest(HttpServletRequest request)
    {
        final String bearer = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearer) && bearer.startsWith(JWT_PREFIX))
        {
            return Optional.of(bearer.substring(JWT_PREFIX.length()));
        }

        return Optional.empty();
    }
}

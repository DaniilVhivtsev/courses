package com.fitness.courses.configuration.security.jwt.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fitness.courses.configuration.security.jwt.util.JwtUtils;
import com.fitness.courses.http.user.service.UserService;

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
    private final UserService userService;

    public JwtRequestFilter(
            JwtProvider jwtProvider,
            UserService userService)
    {
        this.jwtProvider = jwtProvider;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        final Optional<String> tokenOptional = getTokenFromRequest(request);

        if (tokenOptional.isPresent() && jwtProvider.validateAccessToken(tokenOptional.get()))
        {
            final Claims claims = jwtProvider.getAccessClaims(tokenOptional.get());
            final UserDetails userDetails = userService.loadUserByUsername(JwtUtils.getUsername(claims));
            final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails == null
                            ? List.of()
                            : userDetails.getAuthorities()
            ); // in constructor set authenticated true

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
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

package com.fitness.courses.configuration.security.jwt;

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

/**
 * Фильтр запроса для установки текущего пользователя по переданному access токену.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter
{
    public static final String AUTHORIZATION = "Authorization";
    public static final String JWT_PREFIX = "Bearer ";

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
        final Optional<String> tokenOptional = getAccessTokenFromRequest(request);

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

    /**
     * Получение токена из запроса по заголовку "Authorization".
     *
     * @param request запрос.
     * @return access токен, иначе пустой Optional.
     */
    private static Optional<String> getAccessTokenFromRequest(HttpServletRequest request)
    {
        final String bearer = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearer) && bearer.startsWith(JWT_PREFIX))
        {
            return Optional.of(bearer.substring(JWT_PREFIX.length()));
        }

        return Optional.empty();
    }
}

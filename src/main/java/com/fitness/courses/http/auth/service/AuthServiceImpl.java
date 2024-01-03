package com.fitness.courses.http.auth.service;

import java.util.HashMap;

import net.bytebuddy.utility.RandomString;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitness.courses.global.exceptions.BadRequestException;
import com.fitness.courses.global.exceptions.AuthorizedException;
import com.fitness.courses.global.exceptions.ValidationException;
import com.fitness.courses.http.html.service.VerificationCodeHTMLService;
import com.fitness.courses.http.auth.dto.JwtResponse;
import com.fitness.courses.http.auth.dto.LoginRequestDto;
import com.fitness.courses.http.auth.dto.RegistrationUserInfoDto;
import com.fitness.courses.configuration.security.jwt.JwtProvider;
import com.fitness.courses.http.user.model.Role;
import com.fitness.courses.http.user.model.User;
import com.fitness.courses.http.user.service.UserService;

import io.jsonwebtoken.Claims;
import jakarta.mail.MessagingException;

@Service
public class AuthServiceImpl implements AuthService
{
    private static final @NotNull Logger LOG = LoggerFactory.getLogger(AuthServiceImpl.class);

    private static final Map<String, String> refreshStorage = new HashMap<>();

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final VerificationCodeHTMLService verificationCodeHTMLService;
    private final AuthValidationService authValidationService;

    @Autowired
    public AuthServiceImpl(UserService userService,
            PasswordEncoder passwordEncoder,
            JwtProvider jwtProvider,
            VerificationCodeHTMLService verificationCodeHTMLService,
            AuthValidationService authValidationService)
    {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.verificationCodeHTMLService = verificationCodeHTMLService;
        this.authValidationService = authValidationService;
    }

    @Override
    public @NotNull JwtResponse login(@NotNull LoginRequestDto loginRequestDto)
    {
        authValidationService.validateUserExistByEmail(loginRequestDto.getLogin());
        authValidationService.validateUserLoginCredentials(loginRequestDto);
        authValidationService.validateUserEmailIsConfirmed(loginRequestDto.getLogin());

        final User user = userService.findByEmail(loginRequestDto.getLogin()).get();
        final String accessToken = jwtProvider.generateAccessToken(user);
        final String refreshToken = jwtProvider.generateRefreshToken(user);
        refreshStorage.put(user.getEmail(), refreshToken);

        return new JwtResponse()
                .setAccessToken(accessToken)
                .setRefreshToken(refreshToken);
    }

    @Override
    public @NotNull JwtResponse refresh(@NotNull String refreshToken)
    {
        if (jwtProvider.validateRefreshToken(refreshToken))
        {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            if (!refreshStorage.containsKey(login))
            {
                final String message = "Can't find refresh token by login";
                final String messageForLog = "Can't find refresh token by login %s".formatted(login);
                LOG.error(messageForLog);
                throw new BadRequestException(message);
            }
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken.equals(refreshToken))
            {
                final User user = userService.findByEmail(login).orElseThrow();
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                refreshStorage.put(user.getEmail(), newRefreshToken);
                return new JwtResponse()
                        .setAccessToken(accessToken)
                        .setRefreshToken(newRefreshToken);
            }
            else
            {
                final String message = "Refresh token isn't current.";
                LOG.error(message);
                throw new ValidationException(message);
            }
        }

        final String message = "Refresh token isn't valid";
        LOG.error(message);
        throw new ValidationException(message);
    }

    @Override
    @Transactional
    public void registration(@NotNull RegistrationUserInfoDto registrationUserInfoDto)
    {
        authValidationService.validateUserIsNotExistByEmail(registrationUserInfoDto.getEmail());

        // TODO add mapper
        User newUser = new User();
        newUser.setEmail(registrationUserInfoDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(registrationUserInfoDto.getPassword()));
        newUser.setRoles(Set.of(Role.USER));
        newUser.setFirstName(registrationUserInfoDto.getFirstName());
        newUser.setLastName(registrationUserInfoDto.getLastName());
        newUser.setConfirmed(false);

        Long newUserId = userService.save(newUser).getId();

        sendVerificationEmailCode(newUserId);
    }

    @Override
    public void confirmEmailByCode(String userIdString, String confirmCode)
    {
        Long userId = Long.parseLong(userIdString);
        User user = userService.findById(userId).orElseThrow();

        if (user.getLastVerificationEmailCode().equals(confirmCode))
        {
            user.setConfirmed(true);
            user.setLastVerificationEmailCode("");
            userService.save(user);
        }
        else
        {
            throw new RuntimeException();
        }
    }

    @Override
    public User getCurrentUserOrThrow() throws AuthorizedException
    {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken)
        {
            final String message = "User is not authorized";
            LOG.error(message);
            throw new AuthorizedException(message);
        }

        return (User) authentication.getPrincipal();
    }

    @Override
    public Optional<User> getCurrentUser()
    {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken)
        {
            return Optional.empty();
        }

        return Optional.of((User) authentication.getPrincipal());
    }

    private void sendVerificationEmailCode(Long userId)
    {
        final Optional<User> userOptional = userService.findById(userId);

        if (userOptional.isEmpty())
        {
            throw new RuntimeException();
        }

        User user = userOptional.get();

        String randomCode = RandomString.make(64);
        user.setLastVerificationEmailCode(randomCode);
        user.setConfirmed(false);

        try
        {
            verificationCodeHTMLService.sendVerificationEmailCode(user.getEmail(), randomCode, user.getId());
        }
        catch (MessagingException e)
        {
            e.printStackTrace();
        }
    }
}

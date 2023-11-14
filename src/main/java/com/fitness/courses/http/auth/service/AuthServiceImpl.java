package com.fitness.courses.http.auth.service;

import java.util.HashMap;
import net.bytebuddy.utility.RandomString;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitness.courses.http.html.service.VerificationCodeHTMLService;
import com.fitness.courses.http.auth.dto.JwtResponse;
import com.fitness.courses.http.auth.dto.LoginRequest;
import com.fitness.courses.http.auth.dto.RefreshTokenDto;
import com.fitness.courses.http.auth.dto.RegistrationUserInfoDto;
import com.fitness.courses.configuration.security.jwt.service.JwtProvider;
import com.fitness.courses.http.user.model.Role;
import com.fitness.courses.http.user.model.User;
import com.fitness.courses.http.user.service.UserService;

import io.jsonwebtoken.Claims;
import jakarta.mail.MessagingException;

@Service
public class AuthServiceImpl implements AuthService
{
    private static final Map<String, String> refreshStorage = new HashMap<>();

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final VerificationCodeHTMLService verificationCodeHTMLService;

    @Autowired
    public AuthServiceImpl(UserService userService,
            PasswordEncoder passwordEncoder, JwtProvider jwtProvider,
            AuthenticationManager authenticationManager,
            VerificationCodeHTMLService verificationCodeHTMLService)
    {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
        this.verificationCodeHTMLService = verificationCodeHTMLService;
    }

    @Override
    public @NotNull JwtResponse login(@NotNull LoginRequest loginRequest) throws MessagingException
    {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));

        if (authentication.isAuthenticated())
        {
            final User user = userService.findByEmail(loginRequest.getLogin()).orElseThrow();
            if (!user.isConfirmed())
            {
                sendVerificationEmailCode(user.getId());
                throw new RuntimeException("Please confirm email");
            }
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            refreshStorage.put(user.getEmail(), refreshToken);

            return new JwtResponse()
                    .setAccessToken(accessToken)
                    .setRefreshToken(refreshToken);
        }
        else
        {
            throw new RuntimeException();
        }
    }

    @Override
    public @NotNull JwtResponse refresh(@NotNull RefreshTokenDto refreshTokenDto)
    {
        if (jwtProvider.validateRefreshToken(refreshTokenDto.getRefreshToken())) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshTokenDto.getRefreshToken());
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshTokenDto.getRefreshToken())) {
                final User user = userService.findByEmail(login).orElseThrow();
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                refreshStorage.put(user.getEmail(), newRefreshToken);
                return new JwtResponse()
                        .setAccessToken(accessToken)
                        .setRefreshToken(newRefreshToken);
            }
        }

        // throw exception
        return new JwtResponse();
    }

    @Override
    @Transactional
    public void registration(@NotNull RegistrationUserInfoDto registrationUserInfoDto) throws MessagingException
    {
        final Optional<User> userOptional = userService.findByEmail(registrationUserInfoDto.getEmail());

        if (userOptional.isPresent())
        {
            throw new RuntimeException();
        }

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

    private void sendVerificationEmailCode(Long userId) throws MessagingException
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

        verificationCodeHTMLService.sendVerificationEmailCode(user.getEmail(), randomCode, user.getId());
    }
}

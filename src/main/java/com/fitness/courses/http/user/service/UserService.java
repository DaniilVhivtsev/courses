package com.fitness.courses.http.user.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.fitness.courses.http.user.model.User;

public interface UserService extends UserDetailsService
{
    Optional<User> findByEmail(String email);

    User update(User user);

    Optional<User> findById(Long id);

    User save(User user);
}

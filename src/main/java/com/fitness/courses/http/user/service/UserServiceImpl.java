package com.fitness.courses.http.user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitness.courses.http.user.model.User;
import com.fitness.courses.http.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService
{
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException
    {
        return userRepository.findByEmail(username).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public User update(User user)
    {
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id)
    {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public User save(User user)
    {
        return userRepository.save(user);
    }
}

package com.fitness.courses.http.user.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * Роли пользователя, которые необходимы для определения ограничений на уровне авторизации.
 */
public enum Role implements GrantedAuthority
{

    ADMIN("ADMIN"),
    USER("USER");

    private final String value;

    Role(String value)
    {
        this.value = value;
    }

    @Override
    public String getAuthority()
    {
        return value;
    }

}

package com.fitness.courses.configuration.security.jwt.util;

/**
 * Класс с константами предназначенными для JWT.
 */
public class JwtConstants
{
    private JwtConstants()
    {
    }

    /**
     * Ключ с ролями пользователя в полезных данных JWT.
     */
    public static final String CLAIM_USER_ROLES_KEY = "roles";

    /**
     * Ключ с идентификатором пользователя в полезных данных JWT.
     */
    public static final String CLAIM_USER_ID_KEY = "id";
}

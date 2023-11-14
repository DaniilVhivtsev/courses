package com.fitness.courses.configuration.email.util;

import javax.validation.constraints.NotNull;

/**
 * Константы для конфигурации электронной почты.
 */
public class EmailConfigConstants
{
    private EmailConfigConstants()
    {
    }

    /**
     * Хост электронной почты.
     */
    public static final @NotNull String MAIL_SENDER_HOST = "smtp.mail.ru";

    /**
     * Порт электронной почты.
     */
    public static final int MAIL_SENDER_PORT = 465;

    /**
     * Ключ свойства "Протокол электронной почты".
     */
    public static final @NotNull String EMAIL_PROTOCOL_PROPERTY_KEY = "mail.transport.protocol";

    /**
     * SMTPS протокол электронной почты.
     */
    public static final @NotNull String SMTPS_EMAIL_PROTOCOL = "smtps";

    /**
     * Ключ свойства "Использовать или нет аутентификацию SMTP".
     */
    public static final @NotNull String SMTP_PROPERTY_KEY_TO_ENABLE_AUTH = "mail.smtp.auth";

    /**
     * Ключ свойства "Использовать ли команду STARTTLS (если поддерживается сервером) для переключения соединения на
     * соединение, защищенное TLS, перед выполнением каких-либо команд входа в систему".
     */
    public static final @NotNull String SMTP_PROPERTY_KEY_TO_ENABLE_STARTTLS = "mail.smtp.starttls.enable";

    /**
     * Ключ свойства "Использовать ли SSL для подключения и порт SSL по умолчанию".
     */
    public static final @NotNull String SMTP_PROPERTY_KEY_TO_ENABLE_SSL = "mail.smtp.ssl.enable";

    /**
     * Ключ свойства "Логировать ли все процессы связанные со взаимодействием с электронной почтой".
     */
    public static final @NotNull String PROPERTY_KEY_TO_USE_DEBUG_MODE = "mail.debug";
}

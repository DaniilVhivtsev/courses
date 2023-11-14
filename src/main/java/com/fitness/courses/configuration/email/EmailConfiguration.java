package com.fitness.courses.configuration.email;

import static com.fitness.courses.configuration.email.util.EmailConfigConstants.EMAIL_PROTOCOL_PROPERTY_KEY;
import static com.fitness.courses.configuration.email.util.EmailConfigConstants.MAIL_SENDER_HOST;
import static com.fitness.courses.configuration.email.util.EmailConfigConstants.MAIL_SENDER_PORT;
import static com.fitness.courses.configuration.email.util.EmailConfigConstants.PROPERTY_KEY_TO_USE_DEBUG_MODE;
import static com.fitness.courses.configuration.email.util.EmailConfigConstants.SMTPS_EMAIL_PROTOCOL;
import static com.fitness.courses.configuration.email.util.EmailConfigConstants.SMTP_PROPERTY_KEY_TO_ENABLE_AUTH;
import static com.fitness.courses.configuration.email.util.EmailConfigConstants.SMTP_PROPERTY_KEY_TO_ENABLE_SSL;
import static com.fitness.courses.configuration.email.util.EmailConfigConstants.SMTP_PROPERTY_KEY_TO_ENABLE_STARTTLS;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import io.jsonwebtoken.io.Decoders;

/**
 * Конфигурационный класс для электронной почты.
 */
@Configuration
public class EmailConfiguration
{
    private final @NotNull String emailSenderName;
    private final @NotNull String emailSenderPassword;

    public EmailConfiguration(
            @Value("${email.sender.name}") String emailSenderNameEncoded,
            @Value("${email.sender.password}") String emailSenderPasswordEncoded)
    {
        this.emailSenderName = new String(Decoders.BASE64.decode(emailSenderNameEncoded), StandardCharsets.UTF_8);
        this.emailSenderPassword = new String(Decoders.BASE64.decode(emailSenderPasswordEncoded),
                StandardCharsets.UTF_8);
    }

    /**
     * Сконфигурировать с локальными настройками объект реализующий интерфейс {@link JavaMailSender}.
     *
     * @return объект реализующий интерфейс {@link JavaMailSender}.
     */
    @Bean
    public JavaMailSender getJavaMailSender()
    {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(MAIL_SENDER_HOST);
        javaMailSender.setPort(MAIL_SENDER_PORT);
        javaMailSender.setUsername(emailSenderName);
        javaMailSender.setPassword(emailSenderPassword);

        Properties properties = javaMailSender.getJavaMailProperties();
        properties.put(EMAIL_PROTOCOL_PROPERTY_KEY, SMTPS_EMAIL_PROTOCOL);
        properties.put(SMTP_PROPERTY_KEY_TO_ENABLE_AUTH, Boolean.TRUE);
        properties.put(SMTP_PROPERTY_KEY_TO_ENABLE_STARTTLS, Boolean.TRUE);
        properties.put(SMTP_PROPERTY_KEY_TO_ENABLE_SSL, Boolean.TRUE);
        properties.put(PROPERTY_KEY_TO_USE_DEBUG_MODE, Boolean.TRUE);

        return javaMailSender;
    }
}

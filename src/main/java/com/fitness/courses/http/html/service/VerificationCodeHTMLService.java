package com.fitness.courses.http.html.service;

import jakarta.mail.MessagingException;

public interface VerificationCodeHTMLService
{
    void sendVerificationEmailCode(String to, String code, Long userId) throws MessagingException;
}

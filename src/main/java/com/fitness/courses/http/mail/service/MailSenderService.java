package com.fitness.courses.http.mail.service;

import java.io.InputStream;

import jakarta.mail.MessagingException;

public interface MailSenderService
{
    void sendHtml(String mailAddressTo, String theme, InputStream html) throws MessagingException;
}

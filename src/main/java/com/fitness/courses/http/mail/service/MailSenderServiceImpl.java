package com.fitness.courses.http.mail.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailSenderServiceImpl implements MailSenderService
{
    private final JavaMailSender javaMailSender;

    @Autowired
    public MailSenderServiceImpl(JavaMailSender javaMailSender)
    {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendHtml(String mailAddressTo, String theme, InputStream html) throws MessagingException
    {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage mimeMessage = new MimeMessage(session);
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage(html);
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(mailAddressTo));
        mimeMessage.setFrom("fitness.courses@mail.ru");
        mimeMessage.setSubject(theme);
        mimeMessage.setText(new BufferedReader(new InputStreamReader(html, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n")), "utf-8", "html");

        javaMailSender.send(mimeMessage);
    }
}

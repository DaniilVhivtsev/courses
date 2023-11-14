package com.fitness.courses.http.html.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.courses.http.mail.service.MailSenderService;

import jakarta.mail.MessagingException;

@Service
public class VerificationCodeHTMLServiceImpl implements VerificationCodeHTMLService
{
    private final MailSenderService mailSenderService;

    @Autowired
    public VerificationCodeHTMLServiceImpl(MailSenderService mailSenderService)
    {
        this.mailSenderService = mailSenderService;
    }

    @Override
    public void sendVerificationEmailCode(String to, String code, Long userId) throws MessagingException
    {
        BufferedReader html_file = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("templates/VerificationEmail.html"))));
        //                getClass().getResourceAsStream("../../../../../../templates/VerificationEmail.html")));

        String html = "";
        html += String.join("", Arrays.copyOf(html_file.lines().limit(69).toArray(),
                69, String[].class));
        html += MessageFormat
                .format("<a name=\"verification\" align=\"center\" "
                                + "href=\"http://localhost:3000/auth/verifyEmail?code={0}&secondeCode"
                                + "={1}\">Подтвердить почту</a>",
                        code, userId);
        List<String> lastStrings =
                new BufferedReader(new InputStreamReader(
                        Objects.requireNonNull(
                                this.getClass().getClassLoader().getResourceAsStream("templates/VerificationEmail.html")))).lines()
                        .skip(69).toList();
        html += String.join("", Arrays.copyOf(lastStrings.toArray(), lastStrings.size(), String[].class));

        mailSenderService.sendHtml(to, "Подтверждение регистрации.",
                new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8)));
    }
}

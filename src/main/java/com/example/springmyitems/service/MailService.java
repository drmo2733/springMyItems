package com.example.springmyitems.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final MailSender mailSender;

    @Async
    public void sendMail(String toEmail, String subject, String message) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(toEmail);
        simpleMailMessage.setTo(subject);
        simpleMailMessage.setTo(message);

        mailSender.send(simpleMailMessage);
    }

}

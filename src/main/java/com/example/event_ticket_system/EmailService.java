package com.example.event_ticket_system;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String userEmail, String subject,String emailBody) {
        try {
            MimeMessage emailMsg = mailSender.createMimeMessage();
            MimeMessageHelper msgHelper = new MimeMessageHelper(emailMsg, true, "UTF-8");

            //SimpleMailMessage emailMsg = new SimpleMailMessage();
            msgHelper.setFrom("hristo.lubomirov1@gmail.com");
            msgHelper.setTo(userEmail);
            msgHelper.setSubject(subject);
            msgHelper.setText(emailBody,true);
            mailSender.send(emailMsg);

        } catch (Exception e) {
            throw new ServiceException("We couldn't send the confirmation email.");        }
    }
}

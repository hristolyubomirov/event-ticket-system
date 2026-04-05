package com.example.event_ticket_system;

import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    @Test
    void shouldSendEmailSuccessfully() {
        String email = "test@test.com";
        String subject = "Ticket Confirmation";
        String body = "<h1>Email Sent</h1>";


        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        emailService.sendEmail(email, subject, body);


        verify(mailSender, times(1)).send(any(MimeMessage.class));
    }

    @Test
    void shouldThrowServiceExceptionWhenMailSenderFails() {
        when(mailSender.createMimeMessage()).thenReturn(mock(MimeMessage.class));

        doThrow(new ServiceException("SMTP Server Down")).when(mailSender).send(any(MimeMessage.class));

        assertThrows(ServiceException.class, () -> {
            emailService.sendEmail("test@test.com", "Sub", "Body");
        });
    }
}
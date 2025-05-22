package com.tyrdanov.notification_service.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.tyrdanov.notification_service.dto.ConfirmEmailRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;

    @KafkaListener(topics = "confirm-email-request-topic", groupId = "confirm-email")
    public void sendMessage(ConfirmEmailRequest request) {
        try {
            final var email = request.getEmail();
            final var confirmationCode = request.getConfirmationCode();
            final var confirmationLink = String.format("http://localhost:8080/api/users/register/%s", confirmationCode);
            final var message = String.format("Hello!%nYour activation link: %s", confirmationLink);
            final var simpleMailMessage = new SimpleMailMessage();

            simpleMailMessage.setTo(email);
            simpleMailMessage.setSubject("Подтверждение регистрации");
            simpleMailMessage.setText(message);
            emailSender.send(simpleMailMessage);
        } catch (MailException e) {
            e.printStackTrace();
        }
    }

}

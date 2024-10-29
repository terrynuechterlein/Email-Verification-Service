package com.main.emailverification.service;

import com.main.emailverification.models.VerificationToken;
import com.main.emailverification.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class VerificationService {

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${verification.confirmation-url}")
    private String confirmationUrlBase;

    @Value("${backend.url}")
    private String backendUrl;

    @Value("${microservice.secret}")
    private String secret;

    public void createVerificationToken(String userId, String eduEmail) {
        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUserId(userId);
        verificationToken.setEduEmail(eduEmail);
        verificationToken.setExpiryDate(LocalDateTime.now().plusHours(24));

        tokenRepository.save(verificationToken);

        // Send verification email
        sendVerificationEmail(eduEmail, token);
    }

    private void sendVerificationEmail(String email, String token) {
        String subject = "Verify Your .edu Email";
        String confirmationUrl = confirmationUrlBase + token;
        String message = "Please click the link below to verify your email:\n" + confirmationUrl;

        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo(email);
        emailMessage.setSubject(subject);
        emailMessage.setText(message);

        mailSender.send(emailMessage);
    }

    public boolean confirmVerificationToken(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token);

        if (verificationToken == null) {
            return false;
        }

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            tokenRepository.delete(verificationToken);
            return false;
        }

        // Notify the main application (ASP.NET backend)
        notifyBackend(verificationToken.getUserId());

        // Delete the token after successful verification
        tokenRepository.delete(verificationToken);
        return true;
    }

    private void notifyBackend(String userId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = backendUrl + "/api/tutors/" + userId + "/confirmTutor?secret=" + secret;
        restTemplate.put(url, null);
    }
}

package com.main.emailverification.controller;

import com.main.emailverification.models.VerificationRequest;
import com.main.emailverification.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * Controller for handling verification requests.
 */
@RestController
@RequestMapping("/api/verify")
public class VerificationController {

    @Autowired
    private VerificationService verificationService;

    /**
     * Endpoint to request email verification.
     * URL: POST /api/verify/request
     */
    @PostMapping("/request")
    public ResponseEntity<?> requestVerification(@RequestBody VerificationRequest request) {
        // Allow terrynuechterlein@gmail.com or emails ending with .edu
        if (!request.getEduEmail().endsWith(".edu") &&
                !request.getEduEmail().equals("terrynuechterlein@gmail.com")) {
            return ResponseEntity.badRequest().body("Invalid email address.");
        }

        verificationService.createVerificationToken(request.getUserId(), request.getEduEmail());
        return ResponseEntity.ok("Verification email sent.");
    }

    /**
     * Endpoint to confirm email verification.
     * URL: GET /api/verify/confirm?token=...
     */
    @GetMapping("/confirm")
    public ResponseEntity<?> confirmVerification(@RequestParam("token") String token) {
        boolean isVerified = verificationService.confirmVerificationToken(token);

        if (isVerified) {
            return ResponseEntity.ok("Email verified successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired token.");
        }
    }
}

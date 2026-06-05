package com.emailservice.emailservice.controller;

import com.emailservice.emailservice.kafka.EmailProducer;
import com.emailservice.emailservice.model.EmailFormat;
import com.emailservice.emailservice.model.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class SignupController {

    private final EmailProducer emailProducer;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest signupRequest) {

        // Build email event and push to Kafka topic
        EmailFormat emailFormat = new EmailFormat(
                signupRequest.getEmail(),
                signupRequest.getName(),
                "Welcome to Our Platform!",
                null // body generated in EmailService
        );

        emailProducer.sendEmailEvent(emailFormat);

        return ResponseEntity.ok("Signup successful! Welcome email sent to: "
                + signupRequest.getEmail());
    }
}

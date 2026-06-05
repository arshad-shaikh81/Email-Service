package com.emailservice.emailservice.service;

import com.emailservice.emailservice.model.EmailFormat;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendActualEmail(EmailFormat emailMessage) {

        System.out.println("TLSEmail Start");
        System.out.println("Sending from: " + fromEmail);
        System.out.println("Sending to:   " + emailMessage.getToEmail());

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(emailMessage.getToEmail());
            helper.setSubject("Welcome to Our Platform! 🎉");
            helper.setText(buildWelcomeEmailBody(emailMessage.getName()), true);

            javaMailSender.send(mimeMessage);

            System.out.println("EMail Sent Successfully!!");

        } catch (Exception e) {
            System.out.println("❌ EMAIL ERROR: " + e.getMessage());
            System.out.println("❌ CAUSE: " + (e.getCause() != null ? e.getCause().getMessage() : "unknown"));
            e.printStackTrace();
        }
    }

    private String buildWelcomeEmailBody(String name) {
        return """
                <html>
                <body style="font-family: Arial, sans-serif; padding: 20px; background-color: #f4f4f4;">
                    <div style="max-width: 600px; margin: auto; background: white;
                                padding: 30px; border-radius: 10px;
                                box-shadow: 0 2px 8px rgba(0,0,0,0.1);">

                        <h2 style="color: #2e86c1;">Welcome, %s! 🎉</h2>

                        <p style="font-size: 16px; color: #333;">
                            Thank you for signing up. Your account has been
                            successfully created.
                        </p>

                        <p style="font-size: 16px; color: #333;">
                            You can now log in and start using our platform.
                        </p>

                        <div style="margin: 30px 0;">
                            <a href="http://localhost:8080"
                               style="background-color: #2e86c1; color: white;
                                      padding: 12px 24px; text-decoration: none;
                                      border-radius: 5px; font-size: 16px;">
                                Go to Platform
                            </a>
                        </div>

                        <hr style="border: none; border-top: 1px solid #eee;"/>
                        <p style="color: gray; font-size: 12px;">
                            If you did not sign up, please ignore this email.
                        </p>
                    </div>
                </body>
                </html>
                """.formatted(name != null ? name : "User");
    }
}
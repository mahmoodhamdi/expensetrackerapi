package alashwah.expensetrackerapi.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendOtpEmail(String toEmail, String otpCode, String type) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);

        if ("EMAIL_VERIFICATION".equals(type)) {
            message.setSubject("Email Verification - Expense Tracker");
            message.setText(String.format(
                    "Hello,\n\n" +
                            "Your email verification code is: %s\n\n" +
                            "This code will expire in 10 minutes.\n\n" +
                            "If you didn't request this code, please ignore this email.\n\n" +
                            "Best regards,\n" +
                            "Expense Tracker Team",
                    otpCode
            ));
        } else if ("PASSWORD_RESET".equals(type)) {
            message.setSubject("Password Reset - Expense Tracker");
            message.setText(String.format(
                    "Hello,\n\n" +
                            "Your password reset code is: %s\n\n" +
                            "This code will expire in 10 minutes.\n\n" +
                            "If you didn't request this code, please ignore this email and your password will remain unchanged.\n\n" +
                            "Best regards,\n" +
                            "Expense Tracker Team",
                    otpCode
            ));
        }

        mailSender.send(message);
    }

    public void sendPasswordResetSuccessEmail(String toEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("Password Changed Successfully - Expense Tracker");
        message.setText(
                "Hello,\n\n" +
                        "Your password has been changed successfully.\n\n" +
                        "If you didn't make this change, please contact our support team immediately.\n\n" +
                        "Best regards,\n" +
                        "Expense Tracker Team"
        );

        mailSender.send(message);
    }

    public void sendWelcomeEmail(String toEmail, String name) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("Welcome to Expense Tracker!");
        message.setText(String.format(
                "Hello %s,\n\n" +
                        "Welcome to Expense Tracker! Your email has been verified successfully.\n\n" +
                        "You can now start tracking your expenses and managing your finances effectively.\n\n" +
                        "Best regards,\n" +
                        "Expense Tracker Team",
                name
        ));

        mailSender.send(message);
    }
}
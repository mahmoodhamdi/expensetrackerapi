package alashwah.expensetrackerapi.auth.service;

import alashwah.expensetrackerapi.auth.entity.Otp;
import alashwah.expensetrackerapi.auth.repository.OtpRepository;
import alashwah.expensetrackerapi.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class OtpService {

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private EmailService emailService;

    private static final int OTP_LENGTH = 6;
    private static final int OTP_EXPIRY_MINUTES = 10;
    private static final SecureRandom random = new SecureRandom();

    public String generateAndSendOtp(User user, Otp.OtpType type) {
        // Generate 6-digit OTP
        String otpCode = generateOtpCode();

        // Create OTP entity
        Otp otp = Otp.builder()
                .code(otpCode)
                .user(user)
                .type(type)
                .expiresAt(LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES))
                .used(false)
                .build();

        otpRepository.save(otp);

        // Send email
        emailService.sendOtpEmail(user.getEmail(), otpCode, type.name());

        return otpCode;
    }

    public boolean verifyOtp(User user, String code, Otp.OtpType type) {
        Otp otp = otpRepository.findByCodeAndUserAndTypeAndUsedFalse(code, user, type)
                .orElse(null);

        if (otp == null) {
            return false;
        }

        if (otp.isExpired()) {
            return false;
        }

        // Mark as used
        otp.setUsed(true);
        otpRepository.save(otp);

        return true;
    }

    public void resendOtp(User user, Otp.OtpType type) {
        // Check if there's a recent OTP (within last 1 minute)
        Otp recentOtp = otpRepository
                .findTopByUserAndTypeAndUsedFalseOrderByCreatedAtDesc(user, type)
                .orElse(null);

        if (recentOtp != null &&
                recentOtp.getCreatedAt().isAfter(LocalDateTime.now().minusMinutes(1))) {
            throw new IllegalStateException("Please wait before requesting a new code");
        }

        generateAndSendOtp(user, type);
    }

    private String generateOtpCode() {
        StringBuilder otp = new StringBuilder(OTP_LENGTH);
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }

    // Clean up expired and used OTPs daily
    @Scheduled(cron = "0 0 2 * * ?") // Run at 2 AM daily
    public void cleanupExpiredOtps() {
        List<Otp> expiredOtps = otpRepository.findByExpiresAtBeforeOrUsedTrue(
                LocalDateTime.now()
        );
        otpRepository.deleteAll(expiredOtps);
    }
}
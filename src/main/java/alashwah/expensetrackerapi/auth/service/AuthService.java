package alashwah.expensetrackerapi.auth.service;

import alashwah.expensetrackerapi.auth.dto.AuthResponse;
import alashwah.expensetrackerapi.auth.dto.LoginRequest;
import alashwah.expensetrackerapi.auth.dto.RegisterRequest;
import alashwah.expensetrackerapi.auth.entity.Otp;
import alashwah.expensetrackerapi.security.util.JwtUtil;
import alashwah.expensetrackerapi.user.entity.User;
import alashwah.expensetrackerapi.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private OtpService otpService;

    @Autowired
    private EmailService emailService;

    public AuthResponse register(RegisterRequest request) {
        // Check if user already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        // Create new user (not enabled until email is verified)
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(false); // User must verify email first

        User savedUser = userRepository.save(user);

        // Generate and send OTP for email verification
        otpService.generateAndSendOtp(savedUser, Otp.OtpType.EMAIL_VERIFICATION);

        // Generate JWT token (user can use it after verification)
        String token = jwtUtil.generateToken(savedUser.getEmail());

        return new AuthResponse(
                token,
                savedUser.getEmail(),
                savedUser.getName(),
                savedUser.getId(),
                false // emailVerified
        );
    }

    public AuthResponse login(LoginRequest request) {
        try {
            // Authenticate user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (DisabledException e) {
            throw new IllegalArgumentException("Email not verified. Please verify your email first.");
        } catch (BadCredentialsException e) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        // Get user details
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(
                token,
                user.getEmail(),
                user.getName(),
                user.getId(),
                user.isEnabled()
        );
    }

    public void verifyEmail(String email, String otpCode) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.isEnabled()) {
            throw new IllegalArgumentException("Email already verified");
        }

        boolean isValid = otpService.verifyOtp(user, otpCode, Otp.OtpType.EMAIL_VERIFICATION);

        if (!isValid) {
            throw new IllegalArgumentException("Invalid or expired OTP code");
        }

        // Enable user account
        user.setEnabled(true);
        userRepository.save(user);

        // Send welcome email
        emailService.sendWelcomeEmail(user.getEmail(), user.getName());
    }

    public void resendVerificationOtp(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.isEnabled()) {
            throw new IllegalArgumentException("Email already verified");
        }

        otpService.resendOtp(user, Otp.OtpType.EMAIL_VERIFICATION);
    }

    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!user.isEnabled()) {
            throw new IllegalArgumentException("Please verify your email first");
        }

        otpService.generateAndSendOtp(user, Otp.OtpType.PASSWORD_RESET);
    }

    public void resetPassword(String email, String otpCode, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        boolean isValid = otpService.verifyOtp(user, otpCode, Otp.OtpType.PASSWORD_RESET);

        if (!isValid) {
            throw new IllegalArgumentException("Invalid or expired OTP code");
        }

        // Update password
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // Send confirmation email
        emailService.sendPasswordResetSuccessEmail(user.getEmail());
    }
}
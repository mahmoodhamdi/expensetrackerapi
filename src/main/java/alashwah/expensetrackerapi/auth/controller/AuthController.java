package alashwah.expensetrackerapi.auth.controller;

import alashwah.expensetrackerapi.auth.dto.*;
import alashwah.expensetrackerapi.auth.service.AuthService;
import alashwah.expensetrackerapi.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "User authentication and password management endpoints")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private MessageSource messageSource;

    private String getMessage(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }

    @PostMapping("/register")
    @Operation(summary = "Register new user", description = "Register a new user account. Email verification required.")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse authResponse = authService.register(request);
        ApiResponse<AuthResponse> response = ApiResponse.success(
                getMessage("success.user.registered.verify.email"),
                authResponse
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Login with email and password")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse authResponse = authService.login(request);
        ApiResponse<AuthResponse> response = ApiResponse.success(
                getMessage("success.user.logged.in"),
                authResponse
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-email")
    @Operation(summary = "Verify email", description = "Verify user email with OTP code")
    public ResponseEntity<ApiResponse<MessageResponse>> verifyEmail(
            @Valid @RequestBody VerifyEmailRequest request) {
        authService.verifyEmail(request.getEmail(), request.getOtpCode());

        MessageResponse messageResponse = new MessageResponse(
                getMessage("success.email.verified")
        );

        ApiResponse<MessageResponse> response = ApiResponse.success(
                getMessage("success.email.verified"),
                messageResponse
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/resend-verification-otp")
    @Operation(summary = "Resend verification OTP", description = "Resend email verification OTP code")
    public ResponseEntity<ApiResponse<MessageResponse>> resendVerificationOtp(
            @Valid @RequestBody ResendOtpRequest request) {
        authService.resendVerificationOtp(request.getEmail());

        MessageResponse messageResponse = new MessageResponse(
                getMessage("success.otp.resent")
        );

        ApiResponse<MessageResponse> response = ApiResponse.success(
                getMessage("success.otp.resent"),
                messageResponse
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Forgot password", description = "Request password reset OTP")
    public ResponseEntity<ApiResponse<MessageResponse>> forgotPassword(
            @Valid @RequestBody ForgotPasswordRequest request) {
        authService.forgotPassword(request.getEmail());

        MessageResponse messageResponse = new MessageResponse(
                getMessage("success.password.reset.otp.sent")
        );

        ApiResponse<MessageResponse> response = ApiResponse.success(
                getMessage("success.password.reset.otp.sent"),
                messageResponse
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Reset password", description = "Reset password using OTP code")
    public ResponseEntity<ApiResponse<MessageResponse>> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request) {
        authService.resetPassword(
                request.getEmail(),
                request.getOtpCode(),
                request.getNewPassword()
        );

        MessageResponse messageResponse = new MessageResponse(
                getMessage("success.password.reset")
        );

        ApiResponse<MessageResponse> response = ApiResponse.success(
                getMessage("success.password.reset"),
                messageResponse
        );
        return ResponseEntity.ok(response);
    }
}
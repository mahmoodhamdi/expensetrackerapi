package alashwah.expensetrackerapi.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String email;
    private String name;
    private Long userId;
    private boolean emailVerified;

    // Constructor for backward compatibility
    public AuthResponse(String token, String email, String name, Long userId) {
        this.token = token;
        this.email = email;
        this.name = name;
        this.userId = userId;
        this.emailVerified = true; // Default for existing code
    }
}
package alashwah.expensetrackerapi.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
// Generic Message Response
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {
    private String message;
}
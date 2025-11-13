package alashwah.expensetrackerapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

// Privacy Policy DTO
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrivacyDto {

    @NotBlank(message = "Language code is required")
    private String languageCode;

    @NotBlank(message = "Privacy content is required")
    private String content;
}
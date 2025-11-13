package alashwah.expensetrackerapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

// About App DTO
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AboutAppDto {

    @NotBlank(message = "Language code is required")
    private String languageCode;

    @NotBlank(message = "About App content is required")
    private String content;
}
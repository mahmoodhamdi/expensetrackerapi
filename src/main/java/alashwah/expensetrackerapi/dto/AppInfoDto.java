package alashwah.expensetrackerapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppInfoDto {

    @NotBlank(message = "Language code is required")
    private String languageCode;

    @NotBlank(message = "About App text is required")
    private String aboutApp;

    @NotBlank(message = "Privacy Policy text is required")
    private String privacyText;
}

package alashwah.expensetrackerapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Create Theme Request
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThemeCreateRequest {

    @NotBlank(message = "Theme type is required")
    @Pattern(regexp = "^(light|dark)$", message = "Theme type must be either 'light' or 'dark'")
    private String typeTheme;

    @NotBlank(message = "Color is required")
    @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", message = "Color must be a valid hex color")
    private String color;
}

// Update Theme Request
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class ThemeUpdateRequest {

    @Pattern(regexp = "^(light|dark)$", message = "Theme type must be either 'light' or 'dark'")
    private String typeTheme;

    @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", message = "Color must be a valid hex color")
    private String color;
}
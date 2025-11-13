package alashwah.expensetrackerapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Add Word Request
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DictionaryAddWordRequest {

    @NotBlank(message = "Word is required")
    @Size(min = 1, max = 100, message = "Word must be between 1 and 100 characters")
    private String word;

    @NotBlank(message = "Translation is required")
    @Size(min = 1, max = 255, message = "Translation must be between 1 and 255 characters")
    private String translation;
}

// Update Translation Request
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class DictionaryUpdateTranslationRequest {

    @NotBlank(message = "Translation is required")
    @Size(min = 1, max = 255, message = "Translation must be between 1 and 255 characters")
    private String translation;
}

// Dictionary Response
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class DictionaryResponse {
    private String word;
    private String translation;
}
package alashwah.expensetrackerapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

// Create Language Request
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LanguageCreateRequest {

    @NotBlank(message = "Language code is required")
    @Size(min = 2, max = 10, message = "Language code must be between 2 and 10 characters")
    private String code;

    @NotBlank(message = "Language name is required")
    @Size(min = 2, max = 100, message = "Language name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Local name is required")
    @Size(min = 2, max = 100, message = "Local name must be between 2 and 100 characters")
    private String nameLocal;

    private Map<String, String> dictionary;
}

// Update Language Request (PUT)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class LanguageUpdateRequest {

    @NotBlank(message = "Language name is required")
    @Size(min = 2, max = 100, message = "Language name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Local name is required")
    @Size(min = 2, max = 100, message = "Local name must be between 2 and 100 characters")
    private String nameLocal;

    private Map<String, String> dictionary;
}

// Patch Language Request (PATCH)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
class LanguagePatchRequest {

    @Size(min = 2, max = 100, message = "Language name must be between 2 and 100 characters")
    private String name;

    @Size(min = 2, max = 100, message = "Local name must be between 2 and 100 characters")
    private String nameLocal;

    private Map<String, String> dictionary;
}

// Language Response
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class LanguageResponse {
    private String code;
    private String name;
    private String nameLocal;
    private Map<String, String> dictionary;
}
package alashwah.expensetrackerapi.controller;

import alashwah.expensetrackerapi.dto.LanguageCreateRequest;
import alashwah.expensetrackerapi.entity.Language;
import alashwah.expensetrackerapi.response.ApiResponse;
import alashwah.expensetrackerapi.service.LanguageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/languages")
@Tag(name = "Language Management", description = "APIs for managing languages")
public class LanguageController {

    @Autowired
    private LanguageService languageService;

    @GetMapping
    @Operation(summary = "Get all languages", description = "Retrieve all languages with optional filtering")
    public ResponseEntity<ApiResponse<List<Language>>> getAllLanguages(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String exclude) {

        List<Language> languages;

        if (code != null) {
            // Filter by specific code
            Language language = languageService.getLanguageByCode(code);
            languages = List.of(language);
        } else if (exclude != null) {
            // Exclude specific code
            languages = languageService.getLanguagesExcluding(exclude);
        } else {
            // Get all
            languages = languageService.getAllLanguages();
        }

        ApiResponse<List<Language>> response = ApiResponse.successWithCount(
                "Languages retrieved successfully",
                languages,
                languages.size()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{code}")
    @Operation(summary = "Get language by code", description = "Retrieve a specific language by its code")
    public ResponseEntity<ApiResponse<Language>> getLanguageByCode(@PathVariable String code) {
        Language language = languageService.getLanguageByCode(code);

        ApiResponse<Language> response = ApiResponse.success(
                "Language retrieved successfully",
                language
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Create new language", description = "Create a new language with dictionary")
    public ResponseEntity<ApiResponse<Language>> createLanguage(
            @Valid @RequestBody LanguageCreateRequest request) {

        Language language = languageService.createLanguage(request);

        ApiResponse<Language> response = ApiResponse.success(
                "Language created successfully",
                language
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{code}")
    @Operation(summary = "Update language (PUT)", description = "Fully update a language")
    public ResponseEntity<ApiResponse<Language>> updateLanguage(
            @PathVariable String code,
            @RequestBody Map<String, Object> updates) {

        Language language = languageService.updateLanguage(code, updates);

        ApiResponse<Language> response = ApiResponse.success(
                "Language updated successfully",
                language
        );

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{code}")
    @Operation(summary = "Partially update language (PATCH)", description = "Partially update a language")
    public ResponseEntity<ApiResponse<Language>> patchLanguage(
            @PathVariable String code,
            @RequestBody Map<String, Object> updates) {

        Language language = languageService.updateLanguage(code, updates);

        ApiResponse<Language> response = ApiResponse.success(
                "Language updated successfully",
                language
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{code}")
    @Operation(summary = "Delete language", description = "Delete a language by its code")
    public ResponseEntity<ApiResponse<Void>> deleteLanguage(@PathVariable String code) {
        languageService.deleteLanguage(code);

        ApiResponse<Void> response = ApiResponse.success(
                "Language deleted successfully",
                null
        );

        return ResponseEntity.ok(response);
    }
}
package alashwah.expensetrackerapi.controller;

import alashwah.expensetrackerapi.dto.DictionaryAddWordRequest;
import alashwah.expensetrackerapi.entity.Dictionary;
import alashwah.expensetrackerapi.response.ApiResponse;
import alashwah.expensetrackerapi.service.DictionaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/languages/{languageCode}/dictionary")
@Tag(name = "Dictionary Management", description = "APIs for managing language dictionaries")
public class DictionaryController {

    @Autowired
    private DictionaryService dictionaryService;

    @GetMapping
    @Operation(summary = "Get all words in dictionary", description = "Retrieve all words and translations for a language")
    public ResponseEntity<ApiResponse<Map<String, String>>> getAllWords(@PathVariable String languageCode) {
        Map<String, String> dictionary = dictionaryService.getAllWords(languageCode);

        ApiResponse<Map<String, String>> response = ApiResponse.success(
                "Dictionary retrieved successfully",
                dictionary
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Add word to dictionary", description = "Add a new word and translation to the dictionary")
    public ResponseEntity<ApiResponse<Dictionary>> addWord(
            @PathVariable String languageCode,
            @Valid @RequestBody DictionaryAddWordRequest request) {

        Dictionary dictionary = dictionaryService.addWord(languageCode, request);

        ApiResponse<Dictionary> response = ApiResponse.success(
                "Word added successfully",
                dictionary
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{word}")
    @Operation(summary = "Get word translation", description = "Get the translation of a specific word")
    public ResponseEntity<ApiResponse<String>> getWordTranslation(
            @PathVariable String languageCode,
            @PathVariable String word) {

        String translation = dictionaryService.getWordTranslation(languageCode, word);

        ApiResponse<String> response = ApiResponse.success(
                "Translation retrieved successfully",
                translation
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{word}")
    @Operation(summary = "Update word translation", description = "Update the translation of a word")
    public ResponseEntity<ApiResponse<Dictionary>> updateWordTranslation(
            @PathVariable String languageCode,
            @PathVariable String word,
            @RequestBody Map<String, String> body) {

        String newTranslation = body.get("translation");
        if (newTranslation == null || newTranslation.isBlank()) {
            throw new IllegalArgumentException("Translation is required");
        }

        Dictionary dictionary = dictionaryService.updateWordTranslation(languageCode, word, newTranslation);

        ApiResponse<Dictionary> response = ApiResponse.success(
                "Translation updated successfully",
                dictionary
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{word}")
    @Operation(summary = "Delete word from dictionary", description = "Remove a word from the dictionary")
    public ResponseEntity<ApiResponse<Void>> deleteWord(
            @PathVariable String languageCode,
            @PathVariable String word) {

        dictionaryService.deleteWord(languageCode, word);

        ApiResponse<Void> response = ApiResponse.success(
                "Word deleted successfully",
                null
        );

        return ResponseEntity.ok(response);
    }
}
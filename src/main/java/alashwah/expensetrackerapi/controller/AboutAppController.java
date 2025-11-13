package alashwah.expensetrackerapi.controller;

import alashwah.expensetrackerapi.dto.AboutAppDto;
import alashwah.expensetrackerapi.dto.PrivacyDto;
import alashwah.expensetrackerapi.entity.AboutApp;
import alashwah.expensetrackerapi.entity.Privacy;
import alashwah.expensetrackerapi.response.ApiResponse;
import alashwah.expensetrackerapi.service.AboutAppService;
import alashwah.expensetrackerapi.service.PrivacyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// About App Controller
@RestController
@RequestMapping("/api/v1/about-app")
@Tag(name = "About App Management", description = "Manage multilingual About App information")
public class AboutAppController {

    @Autowired
    private AboutAppService aboutAppService;

    @GetMapping
    @Operation(summary = "Get all about app content", description = "Retrieve all about app content for all languages")
    public ResponseEntity<ApiResponse<List<AboutApp>>> getAll() {
        List<AboutApp> aboutList = aboutAppService.getAll();
        return ResponseEntity.ok(ApiResponse.successWithCount(
                "About App content retrieved successfully",
                aboutList,
                aboutList.size()
        ));
    }

    @GetMapping("/{languageCode}")
    @Operation(summary = "Get about app by language", description = "Retrieve about app content for a specific language")
    public ResponseEntity<ApiResponse<AboutApp>> getByLanguage(@PathVariable String languageCode) {
        AboutApp aboutApp = aboutAppService.getByLanguage(languageCode);
        return ResponseEntity.ok(ApiResponse.success(
                "About App content retrieved successfully",
                aboutApp
        ));
    }

    @PostMapping
    @Operation(summary = "Create about app content", description = "Add about app content for a new language")
    public ResponseEntity<ApiResponse<AboutApp>> create(@Valid @RequestBody AboutAppDto dto) {
        AboutApp aboutApp = aboutAppService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("About App content created successfully", aboutApp));
    }

    @PutMapping("/{languageCode}")
    @Operation(summary = "Update about app content", description = "Update about app content for an existing language")
    public ResponseEntity<ApiResponse<AboutApp>> update(
            @PathVariable String languageCode,
            @Valid @RequestBody AboutAppDto dto) {
        AboutApp aboutApp = aboutAppService.update(languageCode, dto);
        return ResponseEntity.ok(ApiResponse.success("About App content updated successfully", aboutApp));
    }

    @DeleteMapping("/{languageCode}")
    @Operation(summary = "Delete about app content", description = "Delete about app content for a language")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String languageCode) {
        aboutAppService.delete(languageCode);
        return ResponseEntity.ok(ApiResponse.success("About App content deleted successfully", null));
    }
}

// Privacy Controller
@RestController
@RequestMapping("/api/v1/privacy")
@Tag(name = "Privacy Management", description = "Manage multilingual Privacy Policy information")
class PrivacyController {

    @Autowired
    private PrivacyService privacyService;

    @GetMapping
    @Operation(summary = "Get all privacy content", description = "Retrieve all privacy policy content for all languages")
    public ResponseEntity<ApiResponse<List<Privacy>>> getAll() {
        List<Privacy> privacyList = privacyService.getAll();
        return ResponseEntity.ok(ApiResponse.successWithCount(
                "Privacy content retrieved successfully",
                privacyList,
                privacyList.size()
        ));
    }

    @GetMapping("/{languageCode}")
    @Operation(summary = "Get privacy by language", description = "Retrieve privacy policy content for a specific language")
    public ResponseEntity<ApiResponse<Privacy>> getByLanguage(@PathVariable String languageCode) {
        Privacy privacy = privacyService.getByLanguage(languageCode);
        return ResponseEntity.ok(ApiResponse.success(
                "Privacy content retrieved successfully",
                privacy
        ));
    }

    @PostMapping
    @Operation(summary = "Create privacy content", description = "Add privacy policy content for a new language")
    public ResponseEntity<ApiResponse<Privacy>> create(@Valid @RequestBody PrivacyDto dto) {
        Privacy privacy = privacyService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Privacy content created successfully", privacy));
    }

    @PutMapping("/{languageCode}")
    @Operation(summary = "Update privacy content", description = "Update privacy policy content for an existing language")
    public ResponseEntity<ApiResponse<Privacy>> update(
            @PathVariable String languageCode,
            @Valid @RequestBody PrivacyDto dto) {
        Privacy privacy = privacyService.update(languageCode, dto);
        return ResponseEntity.ok(ApiResponse.success("Privacy content updated successfully", privacy));
    }

    @DeleteMapping("/{languageCode}")
    @Operation(summary = "Delete privacy content", description = "Delete privacy policy content for a language")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String languageCode) {
        privacyService.delete(languageCode);
        return ResponseEntity.ok(ApiResponse.success("Privacy content deleted successfully", null));
    }
}
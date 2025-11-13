package alashwah.expensetrackerapi.controller;

import alashwah.expensetrackerapi.dto.ThemeCreateRequest;
import alashwah.expensetrackerapi.entity.Theme;
import alashwah.expensetrackerapi.response.ApiResponse;
import alashwah.expensetrackerapi.service.ThemeService;
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
@RequestMapping("/api/v1/themes")
@Tag(name = "Theme Management", description = "APIs for managing application themes (light/dark)")
public class ThemeController {

    @Autowired
    private ThemeService themeService;

    @GetMapping
    @Operation(summary = "Get all themes", description = "Retrieve all available themes")
    public ResponseEntity<ApiResponse<List<Theme>>> getAllThemes() {
        List<Theme> themes = themeService.getAllThemes();

        ApiResponse<List<Theme>> response = ApiResponse.successWithCount(
                "Themes retrieved successfully",
                themes,
                themes.size()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{typeTheme}")
    @Operation(summary = "Get themes by type", description = "Retrieve all themes of a specific type (light or dark)")
    public ResponseEntity<ApiResponse<List<Theme>>> getThemesByType(@PathVariable String typeTheme) {
        List<Theme> themes = themeService.getThemesByType(typeTheme);

        ApiResponse<List<Theme>> response = ApiResponse.successWithCount(
                "Themes retrieved successfully",
                themes,
                themes.size()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Get theme by ID", description = "Retrieve a specific theme by its ID")
    public ResponseEntity<ApiResponse<Theme>> getThemeById(@PathVariable Long id) {
        Theme theme = themeService.getThemeById(id);

        ApiResponse<Theme> response = ApiResponse.success(
                "Theme retrieved successfully",
                theme
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Create new theme", description = "Create a new theme (type must be 'light' or 'dark')")
    public ResponseEntity<ApiResponse<Theme>> createTheme(
            @Valid @RequestBody ThemeCreateRequest request) {

        Theme theme = themeService.createTheme(request);

        ApiResponse<Theme> response = ApiResponse.success(
                "Theme created successfully",
                theme
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update theme", description = "Update an existing theme")
    public ResponseEntity<ApiResponse<Theme>> updateTheme(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        String typeTheme = body.get("typeTheme");
        String color = body.get("color");

        Theme theme = themeService.updateTheme(id, typeTheme, color);

        ApiResponse<Theme> response = ApiResponse.success(
                "Theme updated successfully",
                theme
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete theme", description = "Delete a theme by its ID")
    public ResponseEntity<ApiResponse<Void>> deleteTheme(@PathVariable Long id) {
        themeService.deleteTheme(id);

        ApiResponse<Void> response = ApiResponse.success(
                "Theme deleted successfully",
                null
        );

        return ResponseEntity.ok(response);
    }
}
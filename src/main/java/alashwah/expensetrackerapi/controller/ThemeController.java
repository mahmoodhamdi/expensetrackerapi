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
@Tag(name = "Theme Management", description = "APIs for managing application themes")
public class ThemeController {

    @Autowired
    private ThemeService themeService;

    @GetMapping
    @Operation(summary = "Get all themes", description = "Retrieve all available themes")
    public ResponseEntity<ApiResponse<List<Theme>>> getAllThemes() {
        List<Theme> themes = themeService.getAllThemes();

        ApiResponse<List<Theme>> response = ApiResponse.success(
                "Themes retrieved successfully",
                themes
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{typeTheme}")
    @Operation(summary = "Get theme by type", description = "Retrieve a specific theme by its type")
    public ResponseEntity<ApiResponse<Theme>> getThemeByType(@PathVariable String typeTheme) {
        Theme theme = themeService.getThemeByType(typeTheme);

        ApiResponse<Theme> response = ApiResponse.success(
                "Theme retrieved successfully",
                theme
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Create new theme", description = "Create a new theme")
    public ResponseEntity<ApiResponse<Theme>> createTheme(
            @Valid @RequestBody ThemeCreateRequest request) {

        Theme theme = themeService.createTheme(request);

        ApiResponse<Theme> response = ApiResponse.success(
                "Theme created successfully",
                theme
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{typeTheme}")
    @Operation(summary = "Update theme", description = "Update an existing theme")
    public ResponseEntity<ApiResponse<Theme>> updateTheme(
            @PathVariable String typeTheme,
            @RequestBody Map<String, String> body) {

        String color = body.get("color");
        if (color == null || color.isBlank()) {
            throw new IllegalArgumentException("Color is required");
        }

        // Validate hex color format
        if (!color.matches("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$")) {
            throw new IllegalArgumentException("Color must be a valid hex color");
        }

        Theme theme = themeService.updateTheme(typeTheme, color);

        ApiResponse<Theme> response = ApiResponse.success(
                "Theme updated successfully",
                theme
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{typeTheme}")
    @Operation(summary = "Delete theme", description = "Delete a theme by its type")
    public ResponseEntity<ApiResponse<Void>> deleteTheme(@PathVariable String typeTheme) {
        themeService.deleteTheme(typeTheme);

        ApiResponse<Void> response = ApiResponse.success(
                "Theme deleted successfully",
                null
        );

        return ResponseEntity.ok(response);
    }
}
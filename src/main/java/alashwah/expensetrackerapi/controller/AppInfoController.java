package alashwah.expensetrackerapi.controller;

import alashwah.expensetrackerapi.dto.AppInfoDto;
import alashwah.expensetrackerapi.entity.AppInfo;
import alashwah.expensetrackerapi.response.ApiResponse;
import alashwah.expensetrackerapi.service.AppInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/app-info")
@Tag(name = "App Info Management", description = "Manage multilingual About & Privacy information")
public class AppInfoController {

    @Autowired
    private AppInfoService appInfoService;

    @GetMapping
    @Operation(summary = "Get all app info", description = "Retrieve all app info records")
    public ResponseEntity<ApiResponse<List<AppInfo>>> getAll() {
        List<AppInfo> infoList = appInfoService.getAll();
        return ResponseEntity.ok(ApiResponse.success("App info retrieved successfully", infoList));
    }

    @GetMapping("/{languageCode}")
    @Operation(summary = "Get app info by language", description = "Retrieve app info for a specific language")
    public ResponseEntity<ApiResponse<AppInfo>> getByLanguage(@PathVariable String languageCode) {
        AppInfo info = appInfoService.getByLanguage(languageCode);
        return ResponseEntity.ok(ApiResponse.success("App info retrieved successfully", info));
    }

    @PostMapping
    @Operation(summary = "Create new app info", description = "Add app info for a new language")
    public ResponseEntity<ApiResponse<AppInfo>> create(@Valid @RequestBody AppInfoDto dto) {
        AppInfo info = appInfoService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("App info created successfully", info));
    }

    @PutMapping("/{languageCode}")
    @Operation(summary = "Update app info", description = "Update app info for an existing language")
    public ResponseEntity<ApiResponse<AppInfo>> update(
            @PathVariable String languageCode,
            @Valid @RequestBody AppInfoDto dto) {
        AppInfo info = appInfoService.update(languageCode, dto);
        return ResponseEntity.ok(ApiResponse.success("App info updated successfully", info));
    }

    @DeleteMapping("/{languageCode}")
    @Operation(summary = "Delete app info", description = "Delete app info for a language")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String languageCode) {
        appInfoService.delete(languageCode);
        return ResponseEntity.ok(ApiResponse.success("App info deleted successfully", null));
    }
}

package alashwah.expensetrackerapi.service;

import alashwah.expensetrackerapi.dto.ThemeCreateRequest;
import alashwah.expensetrackerapi.entity.Theme;
import alashwah.expensetrackerapi.exception.ResourceNotFoundException;
import alashwah.expensetrackerapi.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ThemeService {

    @Autowired
    private ThemeRepository themeRepository;

    private static final String LIGHT_THEME = "light";
    private static final String DARK_THEME = "dark";

    public List<Theme> getAllThemes() {
        return themeRepository.findAll();
    }

    public List<Theme> getThemesByType(String typeTheme) {
        validateThemeType(typeTheme);
        List<Theme> themes = themeRepository.findByTypeTheme(typeTheme);
        if (themes.isEmpty()) {
            throw new ResourceNotFoundException("No themes found for type: " + typeTheme);
        }
        return themes;
    }

    public Theme getThemeById(Long id) {
        return themeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Theme", "id", id));
    }

    public Theme createTheme(ThemeCreateRequest request) {
        validateThemeType(request.getTypeTheme());

        // Check if theme with same type and color already exists
        if (themeRepository.existsByTypeThemeAndColor(request.getTypeTheme(), request.getColor())) {
            throw new IllegalArgumentException(
                    "Theme with type '" + request.getTypeTheme() +
                            "' and color '" + request.getColor() + "' already exists"
            );
        }

        Theme theme = Theme.builder()
                .typeTheme(request.getTypeTheme())
                .color(request.getColor())
                .build();

        return themeRepository.save(theme);
    }

    public Theme updateTheme(Long id, String typeTheme, String color) {
        Theme theme = getThemeById(id);

        if (typeTheme != null && !typeTheme.isBlank()) {
            validateThemeType(typeTheme);
            theme.setTypeTheme(typeTheme);
        }

        if (color != null && !color.isBlank()) {
            // Validate hex color format
            if (!color.matches("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$")) {
                throw new IllegalArgumentException("Color must be a valid hex color");
            }

            // Check if another theme with same type and color exists
            if (themeRepository.existsByTypeThemeAndColor(theme.getTypeTheme(), color)) {
                Theme existingTheme = themeRepository.findByTypeThemeAndColor(theme.getTypeTheme(), color)
                        .orElseThrow();
                if (!existingTheme.getId().equals(id)) {
                    throw new IllegalArgumentException(
                            "Theme with type '" + theme.getTypeTheme() +
                                    "' and color '" + color + "' already exists"
                    );
                }
            }

            theme.setColor(color);
        }

        return themeRepository.save(theme);
    }

    public void deleteTheme(Long id) {
        Theme theme = getThemeById(id);
        themeRepository.delete(theme);
    }

    private void validateThemeType(String typeTheme) {
        if (!LIGHT_THEME.equals(typeTheme) && !DARK_THEME.equals(typeTheme)) {
            throw new IllegalArgumentException("type_theme must be either 'light' or 'dark'");
        }
    }
}
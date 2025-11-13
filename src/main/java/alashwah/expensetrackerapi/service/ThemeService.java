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

    public List<Theme> getAllThemes() {
        return themeRepository.findAll();
    }

    public Theme getThemeByType(String typeTheme) {
        return themeRepository.findById(typeTheme)
                .orElseThrow(() -> new ResourceNotFoundException("Theme", "type", typeTheme));
    }

    public Theme createTheme(ThemeCreateRequest request) {
        if (themeRepository.existsByTypeTheme(request.getTypeTheme())) {
            throw new IllegalArgumentException("Theme with type '" + request.getTypeTheme() + "' already exists");
        }

        Theme theme = Theme.builder()
                .typeTheme(request.getTypeTheme())
                .color(request.getColor())
                .build();

        return themeRepository.save(theme);
    }

    public Theme updateTheme(String typeTheme, String color) {
        Theme theme = getThemeByType(typeTheme);
        theme.setColor(color);
        return themeRepository.save(theme);
    }

    public void deleteTheme(String typeTheme) {
        Theme theme = getThemeByType(typeTheme);
        themeRepository.delete(theme);
    }
}
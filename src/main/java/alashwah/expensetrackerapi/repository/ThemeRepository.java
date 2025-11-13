package alashwah.expensetrackerapi.repository;

import alashwah.expensetrackerapi.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {

    // Find all themes by type (light or dark)
    List<Theme> findByTypeTheme(String typeTheme);

    // Find specific theme by type and color
    Optional<Theme> findByTypeThemeAndColor(String typeTheme, String color);

    // Check if theme exists with type and color
    boolean existsByTypeThemeAndColor(String typeTheme, String color);

    // Count themes by type
    long countByTypeTheme(String typeTheme);
}
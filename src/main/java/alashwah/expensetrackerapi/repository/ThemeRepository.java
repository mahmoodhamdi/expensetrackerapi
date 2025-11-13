package alashwah.expensetrackerapi.repository;

import alashwah.expensetrackerapi.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, String> {

    // Check if theme exists by type
    boolean existsByTypeTheme(String typeTheme);
}
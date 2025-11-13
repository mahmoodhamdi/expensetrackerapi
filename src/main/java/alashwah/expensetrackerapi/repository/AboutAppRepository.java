package alashwah.expensetrackerapi.repository;

import alashwah.expensetrackerapi.entity.AboutApp;
import alashwah.expensetrackerapi.entity.Privacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// About App Repository
@Repository
public interface AboutAppRepository extends JpaRepository<AboutApp, Long> {
    Optional<AboutApp> findByLanguageCode(String languageCode);
    boolean existsByLanguageCode(String languageCode);
    void deleteByLanguageCode(String languageCode);
}


package alashwah.expensetrackerapi.repository;

import alashwah.expensetrackerapi.entity.Privacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Privacy Repository
@Repository
public interface PrivacyRepository extends JpaRepository<Privacy, Long> {
    Optional<Privacy> findByLanguageCode(String languageCode);
    boolean existsByLanguageCode(String languageCode);
    void deleteByLanguageCode(String languageCode);
}
package alashwah.expensetrackerapi.repository;

import alashwah.expensetrackerapi.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguageRepository extends JpaRepository<Language, String> {

    // Find languages excluding specific code
    List<Language> findByCodeNot(String code);

    // Check if language exists by code
    boolean existsByCode(String code);
}
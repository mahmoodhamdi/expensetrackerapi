package alashwah.expensetrackerapi.repository;

import alashwah.expensetrackerapi.entity.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DictionaryRepository extends JpaRepository<Dictionary, Long> {

    // Find all words for a specific language
    List<Dictionary> findByLanguageCode(String languageCode);

    // Find specific word in a language
    Optional<Dictionary> findByLanguageCodeAndWord(String languageCode, String word);

    // Check if word exists in language
    boolean existsByLanguageCodeAndWord(String languageCode, String word);

    // Delete word from language
    void deleteByLanguageCodeAndWord(String languageCode, String word);
}
package alashwah.expensetrackerapi.service;

import alashwah.expensetrackerapi.dto.LanguageCreateRequest;
import alashwah.expensetrackerapi.entity.Dictionary;
import alashwah.expensetrackerapi.entity.Language;
import alashwah.expensetrackerapi.exception.ResourceNotFoundException;
import alashwah.expensetrackerapi.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class LanguageService {

    @Autowired
    private LanguageRepository languageRepository;

    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }

    public List<Language> getLanguagesExcluding(String excludeCode) {
        return languageRepository.findByCodeNot(excludeCode);
    }

    public Language getLanguageByCode(String code) {
        return languageRepository.findById(code)
                .orElseThrow(() -> new ResourceNotFoundException("Language", "code", code));
    }

    public Language createLanguage(LanguageCreateRequest request) {
        if (languageRepository.existsByCode(request.getCode())) {
            throw new IllegalArgumentException("Language with code '" + request.getCode() + "' already exists");
        }

        Language language = Language.builder()
                .code(request.getCode())
                .name(request.getName())
                .nameLocal(request.getNameLocal())
                .dictionaryEntries(new ArrayList<>())
                .build();

        // Add dictionary entries if provided
        if (request.getDictionary() != null && !request.getDictionary().isEmpty()) {
            for (Map.Entry<String, String> entry : request.getDictionary().entrySet()) {
                Dictionary dict = Dictionary.builder()
                        .language(language)
                        .word(entry.getKey())
                        .translation(entry.getValue())
                        .build();
                language.getDictionaryEntries().add(dict);
            }
        }

        return languageRepository.save(language);
    }

    public Language updateLanguage(String code, Map<String, Object> updates) {
        Language language = getLanguageByCode(code);

        if (updates.containsKey("name")) {
            language.setName((String) updates.get("name"));
        }
        if (updates.containsKey("nameLocal")) {
            language.setNameLocal((String) updates.get("nameLocal"));
        }
        if (updates.containsKey("dictionary")) {
            @SuppressWarnings("unchecked")
            Map<String, String> newDict = (Map<String, String>) updates.get("dictionary");

            // Clear existing dictionary
            language.getDictionaryEntries().clear();

            // Add new entries
            for (Map.Entry<String, String> entry : newDict.entrySet()) {
                Dictionary dict = Dictionary.builder()
                        .language(language)
                        .word(entry.getKey())
                        .translation(entry.getValue())
                        .build();
                language.getDictionaryEntries().add(dict);
            }
        }

        return languageRepository.save(language);
    }

    public void deleteLanguage(String code) {
        Language language = getLanguageByCode(code);
        languageRepository.delete(language);
    }
}
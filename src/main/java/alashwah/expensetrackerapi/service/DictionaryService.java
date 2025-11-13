package alashwah.expensetrackerapi.service;

import alashwah.expensetrackerapi.dto.DictionaryAddWordRequest;
import alashwah.expensetrackerapi.entity.Dictionary;
import alashwah.expensetrackerapi.entity.Language;
import alashwah.expensetrackerapi.exception.ResourceNotFoundException;
import alashwah.expensetrackerapi.repository.DictionaryRepository;
import alashwah.expensetrackerapi.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class DictionaryService {

    @Autowired
    private DictionaryRepository dictionaryRepository;

    @Autowired
    private LanguageRepository languageRepository;

    public Map<String, String> getAllWords(String languageCode) {
        // Check if language exists
        if (!languageRepository.existsByCode(languageCode)) {
            throw new ResourceNotFoundException("Language", "code", languageCode);
        }

        List<Dictionary> words = dictionaryRepository.findByLanguageCode(languageCode);
        return words.stream()
                .collect(Collectors.toMap(Dictionary::getWord, Dictionary::getTranslation));
    }

    public Dictionary addWord(String languageCode, DictionaryAddWordRequest request) {
        Language language = languageRepository.findById(languageCode)
                .orElseThrow(() -> new ResourceNotFoundException("Language", "code", languageCode));

        if (dictionaryRepository.existsByLanguageCodeAndWord(languageCode, request.getWord())) {
            throw new IllegalArgumentException("Word '" + request.getWord() + "' already exists in language '" + languageCode + "'");
        }

        Dictionary dictionary = Dictionary.builder()
                .language(language)
                .word(request.getWord())
                .translation(request.getTranslation())
                .build();

        return dictionaryRepository.save(dictionary);
    }

    public String getWordTranslation(String languageCode, String word) {
        Dictionary dictionary = dictionaryRepository.findByLanguageCodeAndWord(languageCode, word)
                .orElseThrow(() -> new ResourceNotFoundException("Word '" + word + "' not found in language '" + languageCode + "'"));

        return dictionary.getTranslation();
    }

    public Dictionary updateWordTranslation(String languageCode, String word, String newTranslation) {
        Dictionary dictionary = dictionaryRepository.findByLanguageCodeAndWord(languageCode, word)
                .orElseThrow(() -> new ResourceNotFoundException("Word '" + word + "' not found in language '" + languageCode + "'"));

        dictionary.setTranslation(newTranslation);
        return dictionaryRepository.save(dictionary);
    }

    public void deleteWord(String languageCode, String word) {
        if (!dictionaryRepository.existsByLanguageCodeAndWord(languageCode, word)) {
            throw new ResourceNotFoundException("Word '" + word + "' not found in language '" + languageCode + "'");
        }

        dictionaryRepository.deleteByLanguageCodeAndWord(languageCode, word);
    }
}
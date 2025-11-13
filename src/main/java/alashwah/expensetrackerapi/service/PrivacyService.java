package alashwah.expensetrackerapi.service;

import alashwah.expensetrackerapi.dto.PrivacyDto;
import alashwah.expensetrackerapi.entity.Privacy;
import alashwah.expensetrackerapi.exception.ResourceNotFoundException;
import alashwah.expensetrackerapi.repository.PrivacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Privacy Service
@Service
@Transactional
public class PrivacyService {

    @Autowired
    private PrivacyRepository privacyRepository;

    public List<Privacy> getAll() {
        return privacyRepository.findAll();
    }

    public Privacy getByLanguage(String languageCode) {
        return privacyRepository.findByLanguageCode(languageCode)
                .orElseThrow(() -> new ResourceNotFoundException("Privacy", "languageCode", languageCode));
    }

    public Privacy create(PrivacyDto dto) {
        if (privacyRepository.existsByLanguageCode(dto.getLanguageCode())) {
            throw new IllegalArgumentException("Privacy for language '" + dto.getLanguageCode() + "' already exists");
        }

        Privacy privacy = Privacy.builder()
                .languageCode(dto.getLanguageCode())
                .content(dto.getContent())
                .build();

        return privacyRepository.save(privacy);
    }

    public Privacy update(String languageCode, PrivacyDto dto) {
        Privacy privacy = getByLanguage(languageCode);
        privacy.setContent(dto.getContent());
        return privacyRepository.save(privacy);
    }

    public void delete(String languageCode) {
        Privacy privacy = getByLanguage(languageCode);
        privacyRepository.delete(privacy);
    }
}
package alashwah.expensetrackerapi.service;

import alashwah.expensetrackerapi.dto.AboutAppDto;
import alashwah.expensetrackerapi.dto.PrivacyDto;
import alashwah.expensetrackerapi.entity.AboutApp;
import alashwah.expensetrackerapi.entity.Privacy;
import alashwah.expensetrackerapi.exception.ResourceNotFoundException;
import alashwah.expensetrackerapi.repository.AboutAppRepository;
import alashwah.expensetrackerapi.repository.PrivacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// About App Service
@Service
@Transactional
public class AboutAppService {

    @Autowired
    private AboutAppRepository aboutAppRepository;

    public List<AboutApp> getAll() {
        return aboutAppRepository.findAll();
    }

    public AboutApp getByLanguage(String languageCode) {
        return aboutAppRepository.findByLanguageCode(languageCode)
                .orElseThrow(() -> new ResourceNotFoundException("About App", "languageCode", languageCode));
    }

    public AboutApp create(AboutAppDto dto) {
        if (aboutAppRepository.existsByLanguageCode(dto.getLanguageCode())) {
            throw new IllegalArgumentException("About App for language '" + dto.getLanguageCode() + "' already exists");
        }

        AboutApp aboutApp = AboutApp.builder()
                .languageCode(dto.getLanguageCode())
                .content(dto.getContent())
                .build();

        return aboutAppRepository.save(aboutApp);
    }

    public AboutApp update(String languageCode, AboutAppDto dto) {
        AboutApp aboutApp = getByLanguage(languageCode);
        aboutApp.setContent(dto.getContent());
        return aboutAppRepository.save(aboutApp);
    }

    public void delete(String languageCode) {
        AboutApp aboutApp = getByLanguage(languageCode);
        aboutAppRepository.delete(aboutApp);
    }
}

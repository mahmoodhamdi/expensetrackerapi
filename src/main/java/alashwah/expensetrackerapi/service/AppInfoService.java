package alashwah.expensetrackerapi.service;

import alashwah.expensetrackerapi.dto.AppInfoDto;
import alashwah.expensetrackerapi.entity.AppInfo;
import alashwah.expensetrackerapi.exception.ResourceNotFoundException;
import alashwah.expensetrackerapi.repository.AppInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppInfoService {

    @Autowired
    private AppInfoRepository appInfoRepository;

    public List<AppInfo> getAll() {
        return appInfoRepository.findAll();
    }

    public AppInfo getByLanguage(String languageCode) {
        return appInfoRepository.findByLanguageCode(languageCode)
                .orElseThrow(() -> new ResourceNotFoundException("AppInfo", "languageCode", languageCode));
    }

    public AppInfo create(AppInfoDto dto) {
        if (appInfoRepository.existsByLanguageCode(dto.getLanguageCode())) {
            throw new IllegalArgumentException("App info for language '" + dto.getLanguageCode() + "' already exists");
        }

        AppInfo info = AppInfo.builder()
                .languageCode(dto.getLanguageCode())
                .aboutApp(dto.getAboutApp())
                .privacyText(dto.getPrivacyText())
                .build();

        return appInfoRepository.save(info);
    }

    public AppInfo update(String languageCode, AppInfoDto dto) {
        AppInfo info = getByLanguage(languageCode);
        info.setAboutApp(dto.getAboutApp());
        info.setPrivacyText(dto.getPrivacyText());
        return appInfoRepository.save(info);
    }

    public void delete(String languageCode) {
        AppInfo info = getByLanguage(languageCode);
        appInfoRepository.delete(info);
    }
}

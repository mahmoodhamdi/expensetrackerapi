package alashwah.expensetrackerapi.repository;

import alashwah.expensetrackerapi.entity.AppInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppInfoRepository extends JpaRepository<AppInfo, Long> {
    Optional<AppInfo> findByLanguageCode(String languageCode);
    boolean existsByLanguageCode(String languageCode);
}

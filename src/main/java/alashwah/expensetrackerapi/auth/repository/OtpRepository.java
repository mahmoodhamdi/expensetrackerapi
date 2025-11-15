package alashwah.expensetrackerapi.auth.repository;

import alashwah.expensetrackerapi.auth.entity.Otp;
import alashwah.expensetrackerapi.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {

    Optional<Otp> findByCodeAndUserAndTypeAndUsedFalse(
            String code,
            User user,
            Otp.OtpType type
    );

    Optional<Otp> findTopByUserAndTypeAndUsedFalseOrderByCreatedAtDesc(
            User user,
            Otp.OtpType type
    );

    List<Otp> findByExpiresAtBeforeOrUsedTrue(LocalDateTime dateTime);

    void deleteByUser(User user);
}
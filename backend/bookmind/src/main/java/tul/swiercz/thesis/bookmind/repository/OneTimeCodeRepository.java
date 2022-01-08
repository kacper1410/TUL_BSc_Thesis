package tul.swiercz.thesis.bookmind.repository;

import org.springframework.data.repository.CrudRepository;
import tul.swiercz.thesis.bookmind.domain.OneTimeCode;

import java.time.LocalDateTime;
import java.util.Optional;

public interface OneTimeCodeRepository extends CrudRepository<OneTimeCode, Long> {

    Optional<OneTimeCode> getOneTimeCodeByCodeAndExpirationDateAfter(String code, LocalDateTime now);
}

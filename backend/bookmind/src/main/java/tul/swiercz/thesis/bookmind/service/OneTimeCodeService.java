package tul.swiercz.thesis.bookmind.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import tul.swiercz.thesis.bookmind.domain.OneTimeCode;
import tul.swiercz.thesis.bookmind.domain.User;
import tul.swiercz.thesis.bookmind.exception.ExceptionMessages;
import tul.swiercz.thesis.bookmind.exception.NotFoundException;
import tul.swiercz.thesis.bookmind.mapper.AbstractMapper;
import tul.swiercz.thesis.bookmind.repository.OneTimeCodeRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class OneTimeCodeService extends CrudService<OneTimeCode> {

    private final OneTimeCodeRepository oneTimeCodeRepository;

    @Autowired
    public OneTimeCodeService(OneTimeCodeRepository oneTimeCodeRepository) {
        this.oneTimeCodeRepository = oneTimeCodeRepository;
    }

    @Override
    protected CrudRepository<OneTimeCode, Long> getRepository() {
        return oneTimeCodeRepository;
    }

    @Override
    protected AbstractMapper<OneTimeCode> getMapper() {
        return null;
    }

    public OneTimeCode generateRegistrationCode(User user) {
        String code =  RandomStringUtils.random(64, true, true);
        LocalDateTime expirationDate = LocalDateTime.now().plus(1, ChronoUnit.DAYS);
        OneTimeCode oneTimeCode = new OneTimeCode(code, user, expirationDate);
        oneTimeCodeRepository.save(oneTimeCode);
        return oneTimeCode;
    }

    public User getUserToActivate(String code) throws NotFoundException {
        OneTimeCode oneTimeCode = oneTimeCodeRepository.getOneTimeCodeByCodeAndExpirationDateAfter(code, LocalDateTime.now())
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.CODE_NOT_FOUND));
        User user = oneTimeCode.getUser();
        oneTimeCodeRepository.delete(oneTimeCode);
        return user;
    }
}

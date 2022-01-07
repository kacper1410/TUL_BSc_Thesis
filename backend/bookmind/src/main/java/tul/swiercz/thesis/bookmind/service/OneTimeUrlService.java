package tul.swiercz.thesis.bookmind.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import tul.swiercz.thesis.bookmind.domain.OneTimeUrl;
import tul.swiercz.thesis.bookmind.domain.User;
import tul.swiercz.thesis.bookmind.mapper.AbstractMapper;
import tul.swiercz.thesis.bookmind.repository.OneTimeUrlRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class OneTimeUrlService extends CrudService<OneTimeUrl> {

    private final OneTimeUrlRepository oneTimeUrlRepository;

    @Autowired
    public OneTimeUrlService(OneTimeUrlRepository oneTimeUrlRepository) {
        this.oneTimeUrlRepository = oneTimeUrlRepository;
    }

    @Override
    protected CrudRepository<OneTimeUrl, Long> getRepository() {
        return oneTimeUrlRepository;
    }

    @Override
    protected AbstractMapper<OneTimeUrl> getMapper() {
        return null;
    }

    public OneTimeUrl generateRegistrationUrl(User user) {
        String url =  RandomStringUtils.random(64, true, true);
        LocalDateTime expirationDate = LocalDateTime.now().plus(1, ChronoUnit.DAYS);
        OneTimeUrl oneTimeUrl = new OneTimeUrl(url, user, expirationDate);
        oneTimeUrlRepository.save(oneTimeUrl);
        return oneTimeUrl;
    }
}

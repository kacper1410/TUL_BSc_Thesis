package tul.swiercz.thesis.bookmind.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tul.swiercz.thesis.bookmind.domain.OneTimeUrl;
import tul.swiercz.thesis.bookmind.domain.User;
import tul.swiercz.thesis.bookmind.repository.OneTimeUrlRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class OneTimeUrlServiceTest {

    @InjectMocks
    private OneTimeUrlService oneTimeUrlService;

    @Mock
    private OneTimeUrlRepository oneTimeUrlRepository;

    private User user;

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void initFields() {
        user = new User();
    }

    @Test
    void generateRegistrationUrl() {

        LocalDateTime before = LocalDateTime.now().plus(1, ChronoUnit.DAYS).minus(1, ChronoUnit.MILLIS);
        OneTimeUrl oneTimeUrl = oneTimeUrlService.generateRegistrationUrl(user);
        LocalDateTime after = LocalDateTime.now().plus(1, ChronoUnit.DAYS).plus(1, ChronoUnit.MILLIS);

        verify(oneTimeUrlRepository).save(oneTimeUrl);
        assertEquals(64, oneTimeUrl.getUrl().length());
        assertEquals(user, oneTimeUrl.getUser());
        assertTrue(oneTimeUrl.getExpirationDate().isAfter(before));
        assertTrue(oneTimeUrl.getExpirationDate().isBefore(after));
    }
}

package tul.swiercz.thesis.bookmind.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tul.swiercz.thesis.bookmind.domain.OneTimeCode;
import tul.swiercz.thesis.bookmind.domain.User;
import tul.swiercz.thesis.bookmind.exception.NotFoundException;
import tul.swiercz.thesis.bookmind.repository.OneTimeCodeRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OneTimeCodeServiceTest {

    @InjectMocks
    private OneTimeCodeService oneTimeCodeService;

    @Mock
    private OneTimeCodeRepository oneTimeCodeRepository;

    private User user;

    private OneTimeCode oneTimeCode;

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void initFields() {
        user = new User();
        oneTimeCode = new OneTimeCode();
        oneTimeCode.setUser(user);
    }

    @Test
    void generateRegistrationCode() {

        LocalDateTime before = LocalDateTime.now().plus(1, ChronoUnit.DAYS).minus(1, ChronoUnit.MILLIS);
        OneTimeCode oneTimeCode = oneTimeCodeService.generateRegistrationCode(user);
        LocalDateTime after = LocalDateTime.now().plus(1, ChronoUnit.DAYS).plus(1, ChronoUnit.MILLIS);

        verify(oneTimeCodeRepository).save(oneTimeCode);
        assertEquals(64, oneTimeCode.getCode().length());
        assertEquals(user, oneTimeCode.getUser());
        assertTrue(oneTimeCode.getExpirationDate().isAfter(before));
        assertTrue(oneTimeCode.getExpirationDate().isBefore(after));
    }

    @Test
    void getUserToActivate() throws NotFoundException {
        when(oneTimeCodeRepository.getOneTimeCodeByCodeAndExpirationDateAfter(eq("code"), any(LocalDateTime.class)))
                .thenReturn(Optional.ofNullable(oneTimeCode));

        User userToActivate = oneTimeCodeService.getUserToActivate("code");

        verify(oneTimeCodeRepository).delete(oneTimeCode);
        assertEquals(user, userToActivate);
    }
}

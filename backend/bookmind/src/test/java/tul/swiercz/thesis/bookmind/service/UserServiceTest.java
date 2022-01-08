package tul.swiercz.thesis.bookmind.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tul.swiercz.thesis.bookmind.domain.AccessLevel;
import tul.swiercz.thesis.bookmind.domain.OneTimeCode;
import tul.swiercz.thesis.bookmind.domain.User;
import tul.swiercz.thesis.bookmind.exception.InternalException;
import tul.swiercz.thesis.bookmind.exception.NotFoundException;
import tul.swiercz.thesis.bookmind.repository.AccessLevelRepository;
import tul.swiercz.thesis.bookmind.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccessLevelRepository accessLevelRepository;

    @Mock
    private OneTimeCodeService oneTimeCodeService;

    @Mock
    private MailService mailService;

    private User user;

    private AccessLevel accessLevel;

    private OneTimeCode oneTimeCode;

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void initFields() {
        user = new User();
        user.setEmail("email@domain.com");
        user.setPassword("password");

        accessLevel = new AccessLevel();

        oneTimeCode = new OneTimeCode();
        oneTimeCode.setCode("url");
    }

    @Test
    void loadUserByUsername() {
        String username = "username";
        when(userRepository.findUserByUsername(username)).thenReturn(user);

        UserDetails userDetails = userService.loadUserByUsername(username);

        assertEquals(user, userDetails);
    }

    @Test
    void register() throws InternalException {
        when(accessLevelRepository.findByAuthority("ROLE_READER")).thenReturn(Optional.ofNullable(accessLevel));
        when(oneTimeCodeService.generateRegistrationCode(user)).thenReturn(oneTimeCode);
        when(userRepository.save(user)).thenReturn(user);

        userService.register(user);

        verify(userRepository).save(user);
        verify(oneTimeCodeService).generateRegistrationCode(user);
        verify(mailService).sendRegisterConfirmation(eq(user.getEmail()), Mockito.notNull());
        assertTrue(new BCryptPasswordEncoder().matches("password", user.getPassword()));
        assertEquals(1, user.getAuthorities().size());
        assertEquals(accessLevel, user.getAuthorities().get(0));
    }

    @Test
    void confirmUser() throws NotFoundException {
        when(oneTimeCodeService.getUserToActivate("code")).thenReturn(user);
        user.setEnabled(false);

        userService.confirmUser("code");

        assertTrue(user.isEnabled());
        verify(userRepository).save(user);
    }
}

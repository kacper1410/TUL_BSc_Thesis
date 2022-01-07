package tul.swiercz.thesis.bookmind.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import tul.swiercz.thesis.bookmind.domain.AccessLevel;
import tul.swiercz.thesis.bookmind.domain.OneTimeUrl;
import tul.swiercz.thesis.bookmind.domain.User;
import tul.swiercz.thesis.bookmind.exception.InternalException;
import tul.swiercz.thesis.bookmind.repository.AccessLevelRepository;
import tul.swiercz.thesis.bookmind.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private OneTimeUrlService oneTimeUrlService;

    @Mock
    private MailService mailService;

    private User user;

    private AccessLevel accessLevel;

    private OneTimeUrl oneTimeUrl;

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void initFields() {
        user = new User();
        user.setEmail("email@domain.com");

        accessLevel = new AccessLevel();

        oneTimeUrl = new OneTimeUrl();
        oneTimeUrl.setUrl("url");
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
        when(oneTimeUrlService.generateRegistrationUrl(user)).thenReturn(oneTimeUrl);
        when(userRepository.save(user)).thenReturn(user);

        userService.register(user);

        verify(userRepository).save(user);
        verify(oneTimeUrlService).generateRegistrationUrl(user);
        verify(mailService).sendRegisterConfirmation(eq(user.getEmail()), Mockito.notNull());
        assertEquals(1, user.getAuthorities().size());
        assertEquals(accessLevel, user.getAuthorities().get(0));
    }
}

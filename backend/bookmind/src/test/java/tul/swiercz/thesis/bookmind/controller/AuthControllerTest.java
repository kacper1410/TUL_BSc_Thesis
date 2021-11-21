package tul.swiercz.thesis.bookmind.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import tul.swiercz.thesis.bookmind.dto.auth.AuthRequest;
import tul.swiercz.thesis.bookmind.dto.auth.JwtResponse;
import tul.swiercz.thesis.bookmind.security.JwtTokenUtil;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void auth() throws Exception {
        String username = "username";
        String password = "password";
        String token = "token";
        AuthRequest authRequest = new AuthRequest(username, password);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtTokenUtil.generateToken(userDetails)).thenReturn(token);

        ResponseEntity<JwtResponse> response = authController.auth(authRequest);

        verify(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(username, password));
        assertEquals(new JwtResponse(token).getJwtToken(), Objects.requireNonNull(response.getBody()).getJwtToken());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}

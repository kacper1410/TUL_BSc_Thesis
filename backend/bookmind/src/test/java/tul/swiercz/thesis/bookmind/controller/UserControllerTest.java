package tul.swiercz.thesis.bookmind.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tul.swiercz.thesis.bookmind.domain.User;
import tul.swiercz.thesis.bookmind.dto.user.CreateUser;
import tul.swiercz.thesis.bookmind.exception.InternalException;
import tul.swiercz.thesis.bookmind.mapper.UserMapper;
import tul.swiercz.thesis.bookmind.service.UserService;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    private User user;

    private CreateUser createUser;

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void initFields() {
        user = new User();
        user.setUsername("username1");
        user.setPassword("password1");
        user.setEmail("email1");

        createUser = new CreateUser();
        createUser.setUsername("createUsername1");
        createUser.setPassword("createPassword1");
        createUser.setEmail("createEmail1");
    }

    @Test
    void registerUser() throws InternalException {
        when(userMapper.createToUser(createUser)).thenReturn(user);
        when(userService.register(user)).thenReturn(2L);

        ResponseEntity<?> response = userController.registerUser(createUser);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNull(response.getBody());
        assertEquals(URI.create("/users/2"), response.getHeaders().getLocation());
    }
}

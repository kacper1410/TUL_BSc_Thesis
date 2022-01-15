package tul.swiercz.thesis.bookmind.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tul.swiercz.thesis.bookmind.domain.AccessLevel;
import tul.swiercz.thesis.bookmind.domain.User;
import tul.swiercz.thesis.bookmind.dto.user.AccessLevelInfo;
import tul.swiercz.thesis.bookmind.dto.user.CreateUser;
import tul.swiercz.thesis.bookmind.dto.user.UserInfo;
import tul.swiercz.thesis.bookmind.dto.user.UserListInfo;
import tul.swiercz.thesis.bookmind.exception.InternalException;
import tul.swiercz.thesis.bookmind.exception.NotFoundException;
import tul.swiercz.thesis.bookmind.mapper.AccessLevelMapper;
import tul.swiercz.thesis.bookmind.mapper.UserMapper;
import tul.swiercz.thesis.bookmind.service.UserService;

import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private AccessLevelMapper accessLevelMapper;

    @Mock
    private Principal principal;

    private User user;

    private CreateUser createUser;

    private List<User> userList;

    private List<UserListInfo> userListInfos;

    private UserInfo userInfo;

    private AccessLevelInfo accessLevelInfo;

    private AccessLevel accessLevel;

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

        userList = new ArrayList<>();
        userList.add(user);

        userListInfos = new ArrayList<>();

        userInfo = new UserInfo();

        accessLevelInfo = new AccessLevelInfo();

        accessLevel = new AccessLevel();
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

    @Test
    void confirmUser() throws NotFoundException {

        ResponseEntity<?> response = userController.confirmUser("code");

        verify(userService).confirmUser("code");
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getAll() {
        when(userService.getAll()).thenReturn(userList);
        when(userMapper.usersToDtos(userList)).thenReturn(userListInfos);

        ResponseEntity<Iterable<UserListInfo>> response = userController.getAll();

        assertEquals(userListInfos, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getProfile() {
        when(userService.getByUsername("username")).thenReturn(user);
        when(userMapper.userToDto(user)).thenReturn(userInfo);
        when(principal.getName()).thenReturn("username");

        ResponseEntity<UserInfo> response = userController.getProfile(principal);

        assertEquals(userInfo, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void addAccessLevel() throws NotFoundException, InternalException {
        when(accessLevelMapper.dtoToAccessLevel(accessLevelInfo)).thenReturn(accessLevel);

        ResponseEntity<?> response = userController.addAccessLevel(2L, accessLevelInfo);

        verify(userService).addAccessLevel(accessLevel, 2L);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void removeAccessLevel() throws NotFoundException, InternalException {
        when(accessLevelMapper.dtoToAccessLevel(accessLevelInfo)).thenReturn(accessLevel);

        ResponseEntity<?> response = userController.removeAccessLevel(2L, accessLevelInfo);

        verify(userService).removeAccessLevel(accessLevel, 2L);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertNull(response.getBody());
    }
}

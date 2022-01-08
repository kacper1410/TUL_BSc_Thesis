package tul.swiercz.thesis.bookmind.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tul.swiercz.thesis.bookmind.dto.user.CreateUser;
import tul.swiercz.thesis.bookmind.exception.InternalException;
import tul.swiercz.thesis.bookmind.exception.NotFoundException;
import tul.swiercz.thesis.bookmind.mapper.UserMapper;
import tul.swiercz.thesis.bookmind.security.Roles;
import tul.swiercz.thesis.bookmind.service.UserService;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.net.URI;


@Controller
@DenyAll
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/{username}")
    @RolesAllowed(Roles.ADMIN)
    public ResponseEntity<UserDetails> getAll(@PathVariable String username) {
        return ResponseEntity.ok(userService.loadUserByUsername(username));
    }

    @PostMapping("/register")
    @PermitAll
    public ResponseEntity<?> registerUser(@RequestBody CreateUser createUser) throws InternalException {
        Long id = userService.register(userMapper.createToUser(createUser));
        return ResponseEntity.created(URI.create("/users/" + id)).build();
    }

    @PostMapping("/confirm/{code}")
    @PermitAll
    public ResponseEntity<?> confirmUser(@PathVariable String code) throws NotFoundException {
        userService.confirmUser(code);
        return ResponseEntity.noContent().build();
    }
}

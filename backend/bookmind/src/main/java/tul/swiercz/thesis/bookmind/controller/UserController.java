package tul.swiercz.thesis.bookmind.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tul.swiercz.thesis.bookmind.dto.user.AccessLevelInfo;
import tul.swiercz.thesis.bookmind.dto.user.CreateUser;
import tul.swiercz.thesis.bookmind.dto.user.UserInfo;
import tul.swiercz.thesis.bookmind.dto.user.UserListInfo;
import tul.swiercz.thesis.bookmind.exception.InternalException;
import tul.swiercz.thesis.bookmind.exception.NotFoundException;
import tul.swiercz.thesis.bookmind.mapper.AccessLevelMapper;
import tul.swiercz.thesis.bookmind.mapper.UserMapper;
import tul.swiercz.thesis.bookmind.security.Roles;
import tul.swiercz.thesis.bookmind.service.UserService;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.net.URI;
import java.security.Principal;


@Controller
@DenyAll
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    private final AccessLevelMapper accessLevelMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper, AccessLevelMapper accessLevelMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.accessLevelMapper = accessLevelMapper;
    }

    @GetMapping
    @RolesAllowed(Roles.ADMIN)
    public ResponseEntity<Iterable<UserListInfo>> getAll() {
        Iterable<UserListInfo> userListInfos = userMapper.usersToDtos(userService.getAll());
        return ResponseEntity.ok(userListInfos);
    }

    @GetMapping("/profile")
    @RolesAllowed({Roles.READER, Roles.ADMIN, Roles.MODERATOR})
    public ResponseEntity<UserInfo> getProfile(Principal principal) {
        UserInfo userInfo = userMapper.userToDto(userService.getByUsername(principal.getName()));
        return ResponseEntity.ok(userInfo);
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

    @PutMapping("/accessLevel/{userId}")
    @RolesAllowed(Roles.ADMIN)
    public ResponseEntity<?> addAccessLevel(@PathVariable Long userId, @RequestBody AccessLevelInfo accessLevelInfo) throws NotFoundException, InternalException {
        userService.addAccessLevel(accessLevelMapper.dtoToAccessLevel(accessLevelInfo), userId);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/accessLevel/{userId}")
    @RolesAllowed(Roles.ADMIN)
    public ResponseEntity<?> removeAccessLevel(@PathVariable Long userId, @RequestBody AccessLevelInfo accessLevelInfo) throws NotFoundException, InternalException {
        userService.removeAccessLevel(accessLevelMapper.dtoToAccessLevel(accessLevelInfo), userId);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/enable/{userId}")
    @RolesAllowed(Roles.ADMIN)
    public ResponseEntity<?> enableUser(@PathVariable Long userId) throws NotFoundException {
        userService.enableUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/disable/{userId}")
    @RolesAllowed(Roles.ADMIN)
    public ResponseEntity<?> disableUser(@PathVariable Long userId) throws NotFoundException {
        userService.disableUser(userId);
        return ResponseEntity.noContent().build();
    }
}

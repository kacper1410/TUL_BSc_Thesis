package tul.swiercz.thesis.bookmind.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tul.swiercz.thesis.bookmind.security.Roles;
import tul.swiercz.thesis.bookmind.service.UserService;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;


@Controller
@DenyAll
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    @RolesAllowed(Roles.ADMIN)
    public ResponseEntity<UserDetails> getAll(@PathVariable String username) {
        return ResponseEntity.ok(userService.loadUserByUsername(username));
    }
}

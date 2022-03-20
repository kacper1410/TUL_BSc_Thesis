package tul.swiercz.thesis.bookmind.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;

@Controller
@DenyAll
@RequestMapping("/connection")
public class ConnectionController {

    @GetMapping
    @PermitAll
    public ResponseEntity<?> getConnection() {
        return ResponseEntity.ok().build();
    }
}

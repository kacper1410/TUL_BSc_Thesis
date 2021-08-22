package tul.swiercz.thesis.bookmind.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    @GetMapping
    public ResponseEntity<List<String>> getAll() {
        return ResponseEntity.ok(List.of("Book 1", "Book 2"));
    }
}

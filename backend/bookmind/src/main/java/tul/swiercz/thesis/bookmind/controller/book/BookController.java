package tul.swiercz.thesis.bookmind.controller.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import tul.swiercz.thesis.bookmind.dto.book.BookInfo;
import tul.swiercz.thesis.bookmind.dto.book.CreateBook;
import tul.swiercz.thesis.bookmind.mapper.BookMapper;
import tul.swiercz.thesis.bookmind.service.BookService;

import java.net.URI;
import java.net.URISyntaxException;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @Autowired
    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @GetMapping
    public ResponseEntity<Iterable<BookInfo>> getAll() {
        Iterable<BookInfo> bookInfos = bookMapper.booksToDtos(bookService.getAll());
        return ResponseEntity.ok(bookInfos);
    }

    @PostMapping
    public ResponseEntity<Long> add(@RequestBody CreateBook createBook) {
        Long id  = bookService.create(bookMapper.createToBook(createBook));
        return ResponseEntity.created(URI.create("/books/" + id)).build();
    }
}

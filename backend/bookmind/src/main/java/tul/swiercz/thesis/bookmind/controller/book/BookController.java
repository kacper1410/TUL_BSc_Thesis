package tul.swiercz.thesis.bookmind.controller.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tul.swiercz.thesis.bookmind.dto.book.BookInfo;
import tul.swiercz.thesis.bookmind.dto.book.CreateBook;
import tul.swiercz.thesis.bookmind.dto.book.ModifyBook;
import tul.swiercz.thesis.bookmind.mapper.BookMapper;
import tul.swiercz.thesis.bookmind.service.BookService;

import java.net.URI;

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

    @GetMapping("/{id}")
    public ResponseEntity<BookInfo> get(@PathVariable Long id) {
        BookInfo bookInfo = bookMapper.bookToDto(bookService.getById(id));
        return ResponseEntity.ok(bookInfo);
    }

    @PostMapping
    public ResponseEntity<Long> add(@RequestBody CreateBook createBook) {
        Long id  = bookService.create(bookMapper.createToBook(createBook));
        return ResponseEntity.created(URI.create("/books/" + id)).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ModifyBook modifyBook) {
        bookService.update(id, bookMapper.modifyToBook(modifyBook));
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

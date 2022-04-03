package tul.swiercz.thesis.bookmind.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tul.swiercz.thesis.bookmind.dto.book.BookWithShelvesInfo;
import tul.swiercz.thesis.bookmind.dto.book.BookListInfo;
import tul.swiercz.thesis.bookmind.dto.book.CreateBook;
import tul.swiercz.thesis.bookmind.dto.book.ModifyBook;
import tul.swiercz.thesis.bookmind.exception.NotFoundException;
import tul.swiercz.thesis.bookmind.mapper.BookMapper;
import tul.swiercz.thesis.bookmind.security.Roles;
import tul.swiercz.thesis.bookmind.service.BookService;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;

@Controller
@DenyAll
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
    @PermitAll
    public ResponseEntity<Iterable<BookListInfo>> getAll() {
        Iterable<BookListInfo> bookInfos = bookMapper.booksToDtos(bookService.getAll());
        return ResponseEntity.ok(bookInfos);
    }

    @GetMapping("/{id}")
    @PermitAll
    public ResponseEntity<BookListInfo> get(@PathVariable Long id) throws NotFoundException {
        BookListInfo bookInfo = bookMapper.bookToDto(bookService.getById(id));
        return ResponseEntity.ok(bookInfo);
    }

    @GetMapping("/{id}/shelves")
    @RolesAllowed(Roles.READER)
    public ResponseEntity<BookWithShelvesInfo> getWithShelves(@PathVariable Long id, Principal principal) throws NotFoundException {
        BookWithShelvesInfo bookWithShelvesInfo = bookService.getByIdWithShelves(id, principal.getName());
        return ResponseEntity.ok(bookWithShelvesInfo);
    }

    @PostMapping
    @RolesAllowed(Roles.MODERATOR)
    public ResponseEntity<?> add(@RequestBody CreateBook createBook) {
        Long id = bookService.create(bookMapper.createToBook(createBook));
        return ResponseEntity.created(URI.create("/books/" + id)).build();
    }

    @PutMapping("/{id}")
    @RolesAllowed(Roles.MODERATOR)
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid ModifyBook modifyBook) throws NotFoundException {
        bookService.update(id, bookMapper.modifyToBook(modifyBook));
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/{id}")
    @RolesAllowed(Roles.MODERATOR)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

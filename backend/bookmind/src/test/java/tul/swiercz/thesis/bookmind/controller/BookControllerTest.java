package tul.swiercz.thesis.bookmind.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tul.swiercz.thesis.bookmind.domain.Book;
import tul.swiercz.thesis.bookmind.dto.book.BookInfo;
import tul.swiercz.thesis.bookmind.dto.book.CreateBook;
import tul.swiercz.thesis.bookmind.dto.book.ModifyBook;
import tul.swiercz.thesis.bookmind.exception.NotFoundException;
import tul.swiercz.thesis.bookmind.mapper.BookMapper;
import tul.swiercz.thesis.bookmind.service.BookService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @Mock
    private BookMapper bookMapper;

    private List<Book> bookList;

    private Book book1;

    private Book book2;

    private List<BookInfo> bookInfoList;

    private BookInfo bookInfo1;

    private BookInfo bookInfo2;

    private CreateBook createBook;

    private ModifyBook modifyBook;


    @BeforeEach
    void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void initFields() {
        book1 = new Book("title1");
        book2 = new Book("title2");
        bookInfo1 = new BookInfo("title3", "author1");
        bookInfo2 = new BookInfo("title4", "author2");
        createBook = new CreateBook("titleC", "authorC");
        modifyBook = new ModifyBook("titleM", "authorM");

        bookList = new ArrayList<>();
        bookList.add(book1);
        bookList.add(book2);
        bookInfoList = new ArrayList<>();
        bookInfoList.add(bookInfo1);
        bookInfoList.add(bookInfo2);
    }

    @Test
    void getAll() {
        when(bookService.getAll()).thenReturn(bookList);
        when(bookMapper.booksToDtos(bookList)).thenReturn(bookInfoList);

        ResponseEntity<Iterable<BookInfo>> response = bookController.getAll();

        assertEquals(bookInfoList, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void get() throws NotFoundException {
        when(bookService.getById(1L)).thenReturn(book1);
        when(bookMapper.bookToDto(book1)).thenReturn(bookInfo1);

        ResponseEntity<BookInfo> response = bookController.get(1L);

        assertEquals(bookInfo1, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void add() {
        when(bookMapper.createToBook(createBook)).thenReturn(book1);
        when(bookService.create(book1)).thenReturn(1L);

        ResponseEntity<?> response = bookController.add(createBook);

        assertEquals(URI.create("/books/1"), response.getHeaders().getLocation());
        assertNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void update() throws NotFoundException {
        when(bookMapper.modifyToBook(modifyBook)).thenReturn(book1);

        ResponseEntity<?> response = bookController.update(1L, modifyBook);

        verify(bookService).update(1L, book1);
        assertNull(response.getBody());
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    @Test
    void delete() {

        ResponseEntity<?> response = bookController.delete(1L);

        verify(bookService).delete(1L);
        assertNull(response.getBody());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}

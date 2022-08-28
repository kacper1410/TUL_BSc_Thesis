package tul.swiercz.thesis.bookmind.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tul.swiercz.thesis.bookmind.domain.Book;
import tul.swiercz.thesis.bookmind.domain.Shelf;
import tul.swiercz.thesis.bookmind.domain.ShelfBook;
import tul.swiercz.thesis.bookmind.domain.User;
import tul.swiercz.thesis.bookmind.exception.ExceptionMessages;
import tul.swiercz.thesis.bookmind.exception.NotFoundException;
import tul.swiercz.thesis.bookmind.exception.SyncException;
import tul.swiercz.thesis.bookmind.mapper.ShelfMapper;
import tul.swiercz.thesis.bookmind.repository.BookRepository;
import tul.swiercz.thesis.bookmind.repository.ShelfBookRepository;
import tul.swiercz.thesis.bookmind.repository.ShelfRepository;
import tul.swiercz.thesis.bookmind.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShelfServiceTest {

    @InjectMocks
    private ShelfService shelfService;

    @Mock
    private ShelfRepository shelfRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ShelfMapper shelfMapper;

    @Mock
    private ShelfBookRepository shelfBookRepository;

    private Iterable<Shelf> shelves;

    private Shelf shelf1;

    private Shelf shelf2;

    private Book book1;

    private Book book2;

    private Book book3;

    private String username;

    private User user;

    private ShelfBook shelfBook;

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void initFields() {
        username = "username";
        shelf1 = new Shelf("name1");
        shelf1.setId(1L);
        shelf2 = new Shelf("name2");
        shelf2.setId(2L);
        book1 = new Book("bname1");
        book1.setId(1L);
        book2 = new Book("bname2");
        book2.setId(2L);
        book3 = new Book("bname3");
        book3.setId(3L);
        Set<Book> books = new HashSet<>();
        books.add(book1);
        books.add(book2);
        shelf1.setBooks(books);
        user = new User();
        user.setUsername(username);
        shelfBook = new ShelfBook(shelf1, book1);

        shelves = List.of(shelf1, shelf2);
    }

    @Test
    void getShelvesByUsername() {
        when(shelfRepository.findByUserUsername(username)).thenReturn(shelves);

        Iterable<Shelf> shelfIterable = shelfService.getShelvesByUsername(username);

        assertEquals(shelves, shelfIterable);
    }

    @Test
    void create() {
        when(shelfRepository.save(shelf1)).thenReturn(shelf1);
        when(userRepository.findUserByUsername(username)).thenReturn(user);

        shelfService.create(shelf1, username);

        verify(shelfRepository).save(shelf1);
        assertEquals(user, shelf1.getUser());
    }

    @Test
    void update() throws NotFoundException {
        when(shelfRepository.findByIdAndUserUsername(1L, username)).thenReturn(Optional.ofNullable(shelf1));
        when(shelfRepository.findById(1L)).thenReturn(Optional.ofNullable(shelf1));
        doAnswer(invocation -> {
            Shelf arg0 = invocation.getArgument(0, Shelf.class);
            Shelf arg1 = invocation.getArgument(1, Shelf.class);
            arg1.setId(arg0.getId());
            arg1.setName(arg0.getName());
            arg1.setBooks(arg0.getBooks());
            return null;
        }).when(shelfMapper).update(eq(shelf1), any(Shelf.class));

        shelfService.update(1L, shelf2, username);

        verify(shelfRepository).save(shelf1);
    }

    @Test
    void updateException() {
        when(shelfRepository.findByIdAndUserUsername(1L, username)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> shelfService.update(1L, shelf2, username)
        );

        assertEquals(ExceptionMessages.UPDATE_NOT_FOUND, exception.getMessage());
    }

    @Test
    void getShelf() throws NotFoundException {
        when(shelfRepository.findWithBooksByIdAndUserUsername(1L, username))
                .thenReturn(Optional.ofNullable(shelf1));

        Shelf shelf = shelfService.getShelf(1L, username);

        assertEquals(shelf1, shelf);
    }

    @Test
    void getShelfException() {
        when(shelfRepository.findWithBooksByIdAndUserUsername(1L, username))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> shelfService.getShelf(1L, username)
        );

        assertEquals(ExceptionMessages.GET_NOT_FOUND, exception.getMessage());
    }

    @Test
    void addBookToShelf() throws NotFoundException {
        when(shelfRepository.findWithBooksByIdAndUserUsername(1L, username))
                .thenReturn(Optional.ofNullable(shelf1));
        when(shelfBookRepository.findById(any()))
                .thenReturn(Optional.ofNullable(shelfBook));
        when(bookRepository.findById(2L)).thenReturn(Optional.ofNullable(book1));
        shelfBook.setActive(false);

        shelfService.addBookToShelf(1L, 2L, username);

        verify(shelfBookRepository).save(shelfBook);
        assertTrue(shelfBook.isActive());
    }

    @Test
    void addBookToShelfSync() throws NotFoundException, SyncException {
        when(shelfRepository.findWithBooksByIdAndUserUsername(1L, username))
                .thenReturn(Optional.ofNullable(shelf1));
        when(shelfBookRepository.findById(any()))
                .thenReturn(Optional.ofNullable(shelfBook));
        shelfBook.setVersion(5L);
        shelfBook.setActive(false);

        shelfService.addBookToShelfSync(1L, 2L, username, 5L);

        verify(shelfBookRepository).save(shelfBook);
    }

    @Test
    void addBookToShelfSyncException() {
        when(shelfRepository.findWithBooksByIdAndUserUsername(1L, username))
                .thenReturn(Optional.ofNullable(shelf1));
        when(shelfBookRepository.findById(any()))
                .thenReturn(Optional.ofNullable(shelfBook));
        shelfBook.setVersion(4L);
        shelfBook.setActive(false);

        SyncException exception = assertThrows(
                SyncException.class,
                () -> shelfService.addBookToShelfSync(1L, 2L, username, 5L)
        );

        assertEquals(ExceptionMessages.SYNC_ERROR, exception.getMessage());
    }

    @Test
    void addBookToShelfException() {
        when(shelfRepository.findWithBooksByIdAndUserUsername(1L, username))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> shelfService.addBookToShelf(1L, 2L, username)
        );

        assertEquals(ExceptionMessages.UPDATE_NOT_FOUND, exception.getMessage());
    }

    @Test
    void addBookToShelfBookException() {
        when(bookRepository.findById(2L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> shelfService.addBookToShelf(1L, 2L, username)
        );

        assertEquals(ExceptionMessages.UPDATE_NOT_FOUND, exception.getMessage());
    }

    @Test
    void addBookToShelfContains() throws NotFoundException {
        when(shelfRepository.findWithBooksByIdAndUserUsername(1L, username))
                .thenReturn(Optional.ofNullable(shelf1));
        when(bookRepository.findById(2L))
                .thenReturn(Optional.ofNullable(book2));
        when(shelfBookRepository.findById(any()))
                .thenReturn(Optional.ofNullable(shelfBook));
        shelfBook.setActive(false);

        shelfService.addBookToShelf(1L, 2L, username);

        assertTrue(shelfBook.isActive());
        verify(shelfBookRepository, times(1)).save(shelfBook);
    }

    @Test
    void removeBookFromShelf() throws NotFoundException {
        when(shelfRepository.findWithBooksByIdAndUserUsername(1L, username))
                .thenReturn(Optional.ofNullable(shelf1));
        when(bookRepository.findById(2L)).thenReturn(Optional.ofNullable(book2));
        when(shelfBookRepository.findById(any()))
                .thenReturn(Optional.ofNullable(shelfBook));
        shelfBook.setActive(true);

        shelfService.removeBookFromShelf(1L, 2L, username);

        assertFalse(shelfBook.isActive());
        verify(shelfBookRepository, times(1)).save(shelfBook);
    }

    @Test
    void removeBookFromShelfWithShelfBookNotFound() throws NotFoundException {
        when(shelfRepository.findWithBooksByIdAndUserUsername(1L, username))
                .thenReturn(Optional.ofNullable(shelf1));
        when(bookRepository.findById(2L)).thenReturn(Optional.ofNullable(book2));

        shelfService.removeBookFromShelf(1L, 2L, username);

        verify(shelfBookRepository, times(1)).save(any(ShelfBook.class));
    }


    @Test
    void removeBookFromShelfSync() throws NotFoundException, SyncException {
        when(shelfRepository.findWithBooksByIdAndUserUsername(1L, username))
                .thenReturn(Optional.ofNullable(shelf1));
        when(shelfBookRepository.findById(any()))
                .thenReturn(Optional.ofNullable(shelfBook));
        shelfBook.setVersion(5L);
        shelfBook.setActive(true);

        shelfService.removeBookFromShelfSync(1L, 2L, username, 5L);

        verify(shelfBookRepository).save(shelfBook);
    }

    @Test
    void removeBookFromShelfSyncException() {
        when(shelfRepository.findWithBooksByIdAndUserUsername(1L, username))
                .thenReturn(Optional.ofNullable(shelf1));
        when(shelfBookRepository.findById(any()))
                .thenReturn(Optional.ofNullable(shelfBook));
        shelfBook.setVersion(4L);
        shelfBook.setActive(true);

        SyncException exception = assertThrows(
                SyncException.class,
                () -> shelfService.removeBookFromShelfSync(1L, 2L, username, 5L)
        );

        assertEquals(ExceptionMessages.SYNC_ERROR, exception.getMessage());
    }

    @Test
    void removeBookFromShelfException() {
        when(shelfRepository.findWithBooksByIdAndUserUsername(1L, username))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> shelfService.removeBookFromShelf(1L, 2L, username)
        );

        assertEquals(ExceptionMessages.UPDATE_NOT_FOUND, exception.getMessage());
    }

    @Test
    void removeBookFromShelfBookException() {
        when(bookRepository.findById(2L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> shelfService.removeBookFromShelf(1L, 2L, username)
        );

        assertEquals(ExceptionMessages.UPDATE_NOT_FOUND, exception.getMessage());
    }

    @Test
    void delete() {
        when(shelfRepository.findByIdAndUserUsername(1L, username)).thenReturn(Optional.ofNullable(shelf1));

        shelfService.delete(1L, username);

        verify(shelfRepository).deleteById(1L);
    }

}

package tul.swiercz.thesis.bookmind.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tul.swiercz.thesis.bookmind.domain.*;
import tul.swiercz.thesis.bookmind.exception.ExceptionMessages;
import tul.swiercz.thesis.bookmind.exception.NotFoundException;
import tul.swiercz.thesis.bookmind.exception.SyncException;
import tul.swiercz.thesis.bookmind.mapper.ShelfMapper;
import tul.swiercz.thesis.bookmind.repository.BookRepository;
import tul.swiercz.thesis.bookmind.repository.ShelfActionRepository;
import tul.swiercz.thesis.bookmind.repository.ShelfRepository;
import tul.swiercz.thesis.bookmind.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

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
    private ShelfActionRepository shelfActionRepository;

    private Iterable<Shelf> shelves;

    private Shelf shelf1;

    private Shelf shelf2;

    private Book book1;

    private Book book2;

    private Book book3;

    private String username;

    private User user;

    private ShelfAction shelfAction;

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void initFields() {
        username = "username";
        shelf1 = new Shelf("name1");
        shelf2 = new Shelf("name2");
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
        shelfAction = new ShelfAction();

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
    void update() throws NotFoundException, SyncException {
        when(shelfRepository.findByIdAndUserUsername(1L, username)).thenReturn(Optional.ofNullable(shelf1));
        when(shelfRepository.findById(1L)).thenReturn(Optional.ofNullable(shelf1));
        when(shelfActionRepository.findByShelfIdAndBookId(1L, null)).thenReturn(null);

        LocalDateTime now = LocalDateTime.now();
        shelfService.update(1L, shelf2, username, now);

        verify(shelfActionRepository).save(argThat((shelfAction) ->
                shelfAction.getShelfActionType().equals(ShelfActionType.UPDATE)
                        && shelfAction.getActionDate().equals(now)
                        && shelfAction.getShelf().equals(shelf1)
                        && shelfAction.getBook() == null)
        );
        verify(shelfRepository).save(shelf1);
    }

    @Test
    void updateWithFoundAction() throws NotFoundException, SyncException {
        when(shelfRepository.findByIdAndUserUsername(1L, username)).thenReturn(Optional.ofNullable(shelf1));
        when(shelfRepository.findById(1L)).thenReturn(Optional.ofNullable(shelf1));
        when(shelfActionRepository.findByShelfIdAndBookId(1L, null)).thenReturn(shelfAction);
        LocalDateTime now = LocalDateTime.now();
        shelfAction.setActionDate(now.minus(20, ChronoUnit.HOURS));

        shelfService.update(1L, shelf2, username, now);

        verify(shelfActionRepository).save(shelfAction);
        assertEquals(now, shelfAction.getActionDate());
        verify(shelfRepository).save(shelf1);
    }

    @Test
    void updateException() {
        when(shelfRepository.findByIdAndUserUsername(1L, username)).thenReturn(Optional.ofNullable(shelf1));
        when(shelfRepository.findById(1L)).thenReturn(Optional.ofNullable(shelf1));
        when(shelfActionRepository.findByShelfIdAndBookId(1L, null)).thenReturn(shelfAction);
        LocalDateTime now = LocalDateTime.now();
        shelfAction.setActionDate(now.plus(20, ChronoUnit.HOURS));

        SyncException exception = assertThrows(
                SyncException.class,
                () -> shelfService.update(1L, shelf2, username, now)
        );

        assertEquals(ExceptionMessages.SYNC_ERROR, exception.getMessage());
    }

    @Test
    void updateSyncException() {
        when(shelfRepository.findByIdAndUserUsername(1L, username)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> shelfService.update(1L, shelf2, username, LocalDateTime.now())
        );

        assertEquals(ExceptionMessages.UPDATE_NOT_FOUND, exception.getMessage());
    }

    @Test
    void getShelf() throws NotFoundException {
        when(shelfRepository.findWithBooksByIdAndUserUsername(1L, username)).thenReturn(Optional.ofNullable(shelf1));

        Shelf shelf = shelfService.getShelf(1L, username);

        assertEquals(shelf1, shelf);
    }

    @Test
    void getShelfException() {
        when(shelfRepository.findWithBooksByIdAndUserUsername(1L, username)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> shelfService.getShelf(1L, username)
        );

        assertEquals(ExceptionMessages.GET_NOT_FOUND, exception.getMessage());
    }

    @Test
    void addBookToShelf() throws NotFoundException, SyncException {
        when(shelfRepository.findWithBooksByIdAndUserUsername(1L, username)).thenReturn(Optional.ofNullable(shelf1));
        when(bookRepository.findById(2L)).thenReturn(Optional.ofNullable(book3));
        when(shelfActionRepository.findByShelfIdAndBookId(1L, 2L)).thenReturn(null);

        LocalDateTime now = LocalDateTime.now();
        shelfService.addBookToShelf(1L, 2L, username, now);

        verify(shelfActionRepository).save(argThat((shelfAction) ->
                shelfAction.getShelfActionType().equals(ShelfActionType.ADD_BOOK)
                        && shelfAction.getActionDate().equals(now)
                        && shelfAction.getShelf().equals(shelf1)
                        && shelfAction.getBook().equals(book3))
        );
        verify(shelfRepository).save(shelf1);
        assertEquals(3, shelf1.getBooks().size());
        assertTrue(shelf1.getBooks().contains(book3));
    }

    @Test
    void addBookToShelfWithActionFound() throws NotFoundException, SyncException {
        when(shelfRepository.findWithBooksByIdAndUserUsername(1L, username)).thenReturn(Optional.ofNullable(shelf1));
        when(bookRepository.findById(2L)).thenReturn(Optional.ofNullable(book3));
        when(shelfActionRepository.findByShelfIdAndBookId(1L, 2L)).thenReturn(shelfAction);
        LocalDateTime now = LocalDateTime.now();
        shelfAction.setActionDate(now.minus(20, ChronoUnit.HOURS));

        shelfService.addBookToShelf(1L, 2L, username, now);

        verify(shelfActionRepository).save(shelfAction);
        verify(shelfRepository).save(shelf1);
        assertEquals(now, shelfAction.getActionDate());
        assertEquals(3, shelf1.getBooks().size());
        assertTrue(shelf1.getBooks().contains(book3));
    }

    @Test
    void addBookToShelfSyncException() {
        when(shelfRepository.findWithBooksByIdAndUserUsername(1L, username)).thenReturn(Optional.ofNullable(shelf1));
        when(bookRepository.findById(2L)).thenReturn(Optional.ofNullable(book3));
        when(shelfActionRepository.findByShelfIdAndBookId(1L, 2L)).thenReturn(shelfAction);
        LocalDateTime now = LocalDateTime.now();
        shelfAction.setActionDate(now.plus(20, ChronoUnit.HOURS));

        SyncException exception = assertThrows(
                SyncException.class,
                () -> shelfService.addBookToShelf(1L, 2L, username, LocalDateTime.now())
        );

        assertEquals(ExceptionMessages.SYNC_ERROR, exception.getMessage());
    }

    @Test
    void addBookToShelfException() {
        when(shelfRepository.findWithBooksByIdAndUserUsername(1L, username)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> shelfService.addBookToShelf(1L, 2L, username, LocalDateTime.now())
        );

        assertEquals(ExceptionMessages.UPDATE_NOT_FOUND, exception.getMessage());
    }

    @Test
    void addBookToShelfBookException() {
        when(bookRepository.findById(2L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> shelfService.addBookToShelf(1L, 2L, username, LocalDateTime.now())
        );

        assertEquals(ExceptionMessages.UPDATE_NOT_FOUND, exception.getMessage());
    }

    @Test
    void addBookToShelfContains() throws NotFoundException, SyncException {
        when(shelfRepository.findWithBooksByIdAndUserUsername(1L, username)).thenReturn(Optional.ofNullable(shelf1));
        when(bookRepository.findById(2L)).thenReturn(Optional.ofNullable(book2));

        shelfService.addBookToShelf(1L, 2L, username, LocalDateTime.now());

        verify(shelfRepository, times(1)).save(shelf1);
        assertEquals(2, shelf1.getBooks().size());
        assertTrue(shelf1.getBooks().contains(book2));
    }

    @Test
    void removeBookFromShelf() throws NotFoundException, SyncException {
        when(shelfRepository.findWithBooksByIdAndUserUsername(1L, username)).thenReturn(Optional.ofNullable(shelf1));
        when(bookRepository.findById(2L)).thenReturn(Optional.ofNullable(book2));
        when(shelfActionRepository.findByShelfIdAndBookId(1L, 2L)).thenReturn(null);
        LocalDateTime now = LocalDateTime.now();

        shelfService.removeBookFromShelf(1L, 2L, username, now);


        verify(shelfActionRepository).save(argThat((shelfAction) ->
                shelfAction.getShelfActionType().equals(ShelfActionType.REMOVE_BOOK)
                        && shelfAction.getActionDate().equals(now)
                        && shelfAction.getShelf().equals(shelf1)
                        && shelfAction.getBook().equals(book2))
        );
        verify(shelfRepository).save(shelf1);
        assertEquals(1, shelf1.getBooks().size());
        assertFalse(shelf1.getBooks().contains(book2));
    }

    @Test
    void removeBookFromShelfWithActionFound() throws NotFoundException, SyncException {
        when(shelfRepository.findWithBooksByIdAndUserUsername(1L, username)).thenReturn(Optional.ofNullable(shelf1));
        when(bookRepository.findById(2L)).thenReturn(Optional.ofNullable(book2));
        when(shelfActionRepository.findByShelfIdAndBookId(1L, 2L)).thenReturn(shelfAction);
        LocalDateTime now = LocalDateTime.now();
        shelfAction.setActionDate(now.minus(20, ChronoUnit.HOURS));

        shelfService.removeBookFromShelf(1L, 2L, username, now);

        verify(shelfActionRepository).save(shelfAction);
        assertEquals(now, shelfAction.getActionDate());
        verify(shelfRepository).save(shelf1);
        assertEquals(1, shelf1.getBooks().size());
        assertFalse(shelf1.getBooks().contains(book2));
    }

    @Test
    void removeBookFromShelfSyncException() {
        when(shelfRepository.findWithBooksByIdAndUserUsername(1L, username)).thenReturn(Optional.ofNullable(shelf1));
        when(bookRepository.findById(2L)).thenReturn(Optional.ofNullable(book2));
        when(shelfActionRepository.findByShelfIdAndBookId(1L, 2L)).thenReturn(shelfAction);
        LocalDateTime now = LocalDateTime.now();
        shelfAction.setActionDate(now.plus(20, ChronoUnit.HOURS));

        SyncException exception = assertThrows(
                SyncException.class,
                () -> shelfService.removeBookFromShelf(1L, 2L, username, LocalDateTime.now())
        );

        assertEquals(ExceptionMessages.SYNC_ERROR, exception.getMessage());
    }

    @Test
    void removeBookFromShelfException() {
        when(shelfRepository.findWithBooksByIdAndUserUsername(1L, username)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> shelfService.removeBookFromShelf(1L, 2L, username, LocalDateTime.now())
        );

        assertEquals(ExceptionMessages.UPDATE_NOT_FOUND, exception.getMessage());
    }

    @Test
    void removeBookFromShelfBookException() {
        when(bookRepository.findById(2L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> shelfService.removeBookFromShelf(1L, 2L, username, LocalDateTime.now())
        );

        assertEquals(ExceptionMessages.UPDATE_NOT_FOUND, exception.getMessage());
    }

    @Test
    void removeBookFromShelfNotContains() throws NotFoundException, SyncException {
        when(shelfRepository.findWithBooksByIdAndUserUsername(1L, username)).thenReturn(Optional.ofNullable(shelf1));
        when(bookRepository.findById(2L)).thenReturn(Optional.ofNullable(book3));

        shelfService.removeBookFromShelf(1L, 2L, username, LocalDateTime.now());

        verify(shelfRepository).save(shelf1);
        assertEquals(2, shelf1.getBooks().size());
        assertTrue(shelf1.getBooks().contains(book2));
    }

    @Test
    void delete() {
        when(shelfRepository.findByIdAndUserUsername(1L, username)).thenReturn(Optional.ofNullable(shelf1));

        shelfService.delete(1L, username);

        verify(shelfRepository).deleteById(1L);
    }

}

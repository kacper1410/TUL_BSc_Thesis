package tul.swiercz.thesis.bookmind.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tul.swiercz.thesis.bookmind.domain.Book;
import tul.swiercz.thesis.bookmind.domain.Shelf;
import tul.swiercz.thesis.bookmind.domain.ShelfBook;
import tul.swiercz.thesis.bookmind.dto.book.BookWithShelvesInfo;
import tul.swiercz.thesis.bookmind.dto.shelf.ShelfForBookListInfo;
import tul.swiercz.thesis.bookmind.exception.NotFoundException;
import tul.swiercz.thesis.bookmind.mapper.BookMapper;
import tul.swiercz.thesis.bookmind.mapper.ShelfBookMapper;
import tul.swiercz.thesis.bookmind.repository.BookRepository;
import tul.swiercz.thesis.bookmind.repository.ShelfBookRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ShelfBookRepository shelfBookRepository;

    @Mock
    private ShelfBookMapper shelfBookMapper;

    @Mock
    private BookMapper bookMapper;

    private Shelf shelf1;
    private Shelf shelf2;
    private ShelfForBookListInfo shelfListInfo1;
    private ShelfForBookListInfo shelfListInfo2;
    private Book book1;
    private BookWithShelvesInfo bookWithShelvesInfo;
    private Set<Shelf> shelves;
    private List<ShelfForBookListInfo> shelvesDto;
    private ShelfBook shelfBook1;
    private ShelfBook shelfBook2;
    private Set<ShelfBook> shelfBooks;

    public BookServiceTest() {
    }

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void initFields() {
        shelf1 = new Shelf("name1");
        shelf1.setId(1L);
        shelf2 = new Shelf("name2");
        shelf2.setId(2L);
        shelfListInfo1 = new ShelfForBookListInfo("lname1", 0L,  true);
        shelfListInfo2 = new ShelfForBookListInfo("lname2", 0L, true);
        book1 = new Book("bname1");
        book1.setId(1L);
        bookWithShelvesInfo = new BookWithShelvesInfo();
        bookWithShelvesInfo.setId(1L);
        shelves = Set.of(shelf1, shelf2);
        shelvesDto = List.of(shelfListInfo1, shelfListInfo2);
        shelfBook1 = new ShelfBook(shelf1, book1);
        shelfBook2 = new ShelfBook(shelf2, book1);
        shelfBooks = Set.of(shelfBook1, shelfBook2);
    }

    @Test
    public void testGetByIdWithShelves() throws NotFoundException {
        when(bookRepository.findById(1L)).thenReturn(Optional.ofNullable(book1));
        when(bookMapper.bookToDtoWithShelves(book1)).thenReturn(bookWithShelvesInfo);
        when(shelfBookRepository.findAllByBooksAndShelfUserUsername(book1, "username")).thenReturn(shelfBooks);
        when(shelfBookMapper.mapToShelvesForBook(shelfBooks)).thenReturn(shelvesDto);

        BookWithShelvesInfo returnedBookWithShelvesInfo = bookService.getByIdWithShelves(1L, "username");

        assertEquals(bookWithShelvesInfo, returnedBookWithShelvesInfo);
        assertEquals(shelvesDto, returnedBookWithShelvesInfo.getShelves());
    }
}

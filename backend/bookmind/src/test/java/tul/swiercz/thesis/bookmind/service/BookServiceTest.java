package tul.swiercz.thesis.bookmind.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tul.swiercz.thesis.bookmind.domain.Book;
import tul.swiercz.thesis.bookmind.domain.Shelf;
import tul.swiercz.thesis.bookmind.dto.book.BookWithShelvesInfo;
import tul.swiercz.thesis.bookmind.dto.shelf.ShelfListInfo;
import tul.swiercz.thesis.bookmind.exception.NotFoundException;
import tul.swiercz.thesis.bookmind.mapper.BookMapper;
import tul.swiercz.thesis.bookmind.mapper.ShelfMapper;
import tul.swiercz.thesis.bookmind.repository.BookRepository;
import tul.swiercz.thesis.bookmind.repository.ShelfRepository;

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
    private ShelfRepository shelfRepository;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private ShelfMapper shelfMapper;

    private Shelf shelf1;
    private Shelf shelf2;
    private ShelfListInfo shelfListInfo1;
    private ShelfListInfo shelfListInfo2;
    private Book book1;
    private BookWithShelvesInfo bookWithShelvesInfo;
    private Set<Shelf> shelves;
    private List<ShelfListInfo> shelvesDto;

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
        shelfListInfo1 = new ShelfListInfo("lname1");
        shelfListInfo2 = new ShelfListInfo("lname2");
        book1 = new Book("bname1");
        book1.setId(1L);
        bookWithShelvesInfo = new BookWithShelvesInfo();
        bookWithShelvesInfo.setId(1L);
        shelves = Set.of(shelf1, shelf2);
        shelvesDto = List.of(shelfListInfo1, shelfListInfo2);
    }

    @Test
    public void testGetByIdWithShelves() throws NotFoundException {
        when(bookRepository.findById(1L)).thenReturn(Optional.ofNullable(book1));
        when(bookMapper.bookToDtoWithShelves(book1)).thenReturn(bookWithShelvesInfo);
        when(shelfRepository.findAllByShelfBooksBooksAndUserUsername(book1, "username")).thenReturn(shelves);
        when(shelfMapper.shelfToListInfo(shelves)).thenReturn(shelvesDto);

        BookWithShelvesInfo returnedBookWithShelvesInfo = bookService.getByIdWithShelves(1L, "username");

        assertEquals(bookWithShelvesInfo, returnedBookWithShelvesInfo);
        assertEquals(shelvesDto, returnedBookWithShelvesInfo.getShelves());
    }
}

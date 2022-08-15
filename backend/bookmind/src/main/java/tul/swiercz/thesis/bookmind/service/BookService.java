package tul.swiercz.thesis.bookmind.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import tul.swiercz.thesis.bookmind.domain.Book;
import tul.swiercz.thesis.bookmind.domain.Shelf;
import tul.swiercz.thesis.bookmind.dto.book.BookWithShelvesInfo;
import tul.swiercz.thesis.bookmind.exception.NotFoundException;
import tul.swiercz.thesis.bookmind.mapper.AbstractMapper;
import tul.swiercz.thesis.bookmind.mapper.BookMapper;
import tul.swiercz.thesis.bookmind.mapper.ShelfMapper;
import tul.swiercz.thesis.bookmind.repository.BookRepository;
import tul.swiercz.thesis.bookmind.repository.ShelfRepository;

import javax.transaction.Transactional;
import java.util.Set;

@Service
public class BookService extends CrudService<Book> {

    private final BookRepository bookRepository;

    private final ShelfRepository shelfRepository;

    private final BookMapper bookMapper;

    private final ShelfMapper shelfMapper;

    @Autowired
    public BookService(BookRepository bookRepository, ShelfRepository shelfRepository, BookMapper bookMapper, ShelfMapper shelfMapper) {
        this.bookRepository = bookRepository;
        this.shelfRepository = shelfRepository;
        this.bookMapper = bookMapper;
        this.shelfMapper = shelfMapper;
    }

    @Override
    protected CrudRepository<Book, Long> getRepository() {
        return bookRepository;
    }

    @Override
    protected AbstractMapper<Book> getMapper() {
        return bookMapper;
    }

    @Transactional
    public BookWithShelvesInfo getByIdWithShelves(Long id, String username) throws NotFoundException {
        Book book = getById(id);
        BookWithShelvesInfo bookWithShelvesInfo = bookMapper.bookToDtoWithShelves(book);
        Set<Shelf> shelves = shelfRepository.findAllByShelfBooksBooksAndUserUsername(book, username);
        bookWithShelvesInfo.setShelves(shelfMapper.shelfToListInfo(shelves));
        return bookWithShelvesInfo;
    }
}

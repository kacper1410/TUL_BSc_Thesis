package tul.swiercz.thesis.bookmind.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import tul.swiercz.thesis.bookmind.domain.Book;
import tul.swiercz.thesis.bookmind.domain.ShelfBook;
import tul.swiercz.thesis.bookmind.dto.book.BookWithShelvesInfo;
import tul.swiercz.thesis.bookmind.exception.NotFoundException;
import tul.swiercz.thesis.bookmind.mapper.AbstractMapper;
import tul.swiercz.thesis.bookmind.mapper.BookMapper;
import tul.swiercz.thesis.bookmind.mapper.ShelfBookMapper;
import tul.swiercz.thesis.bookmind.repository.BookRepository;
import tul.swiercz.thesis.bookmind.repository.ShelfBookRepository;

import javax.transaction.Transactional;
import java.util.Set;

@Service
public class BookService extends CrudService<Book> {

    private final BookRepository bookRepository;

    private final ShelfBookRepository shelfBookRepository;

    private final BookMapper bookMapper;

    private final ShelfBookMapper shelfBookMapper;

    @Autowired
    public BookService(BookRepository bookRepository, ShelfBookRepository shelfBookRepository, BookMapper bookMapper, ShelfBookMapper shelfBookMapper) {
        this.bookRepository = bookRepository;
        this.shelfBookRepository = shelfBookRepository;
        this.bookMapper = bookMapper;
        this.shelfBookMapper = shelfBookMapper;
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
        Set<ShelfBook> shelfBooks = shelfBookRepository.findAllByBooksAndShelfUserUsername(book, username);
        bookWithShelvesInfo.setShelves(shelfBookMapper.mapToShelvesForBook(shelfBooks));
        return bookWithShelvesInfo;
    }
}

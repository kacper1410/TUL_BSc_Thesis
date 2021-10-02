package tul.swiercz.thesis.bookmind.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import tul.swiercz.thesis.bookmind.domain.Book;
import tul.swiercz.thesis.bookmind.repository.BookRepository;

@Service
public class BookService extends CRUDService<Book> {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    protected CrudRepository<Book, Long> getRepository() {
        return bookRepository;
    }
}

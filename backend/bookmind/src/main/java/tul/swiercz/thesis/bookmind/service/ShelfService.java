package tul.swiercz.thesis.bookmind.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import tul.swiercz.thesis.bookmind.domain.Book;
import tul.swiercz.thesis.bookmind.domain.Shelf;
import tul.swiercz.thesis.bookmind.domain.ShelfBook;
import tul.swiercz.thesis.bookmind.domain.User;
import tul.swiercz.thesis.bookmind.exception.ExceptionMessages;
import tul.swiercz.thesis.bookmind.exception.NotFoundException;
import tul.swiercz.thesis.bookmind.exception.SyncException;
import tul.swiercz.thesis.bookmind.mapper.AbstractMapper;
import tul.swiercz.thesis.bookmind.mapper.ShelfMapper;
import tul.swiercz.thesis.bookmind.repository.BookRepository;
import tul.swiercz.thesis.bookmind.repository.ShelfBookRepository;
import tul.swiercz.thesis.bookmind.repository.ShelfRepository;
import tul.swiercz.thesis.bookmind.repository.UserRepository;

import javax.transaction.Transactional;

@Service
public class ShelfService extends CrudService<Shelf> {

    private final UserRepository userRepository;

    private final ShelfRepository shelfRepository;

    private final BookRepository bookRepository;

    private final ShelfMapper shelfMapper;

    private final ShelfBookRepository shelfBookRepository;

    @Autowired
    public ShelfService(UserRepository userRepository,
                        ShelfRepository shelfRepository,
                        BookRepository bookRepository,
                        ShelfMapper shelfMapper,
                        ShelfBookRepository shelfBookRepository) {
        this.userRepository = userRepository;
        this.shelfRepository = shelfRepository;
        this.bookRepository = bookRepository;
        this.shelfMapper = shelfMapper;
        this.shelfBookRepository = shelfBookRepository;
    }

    @Override
    protected CrudRepository<Shelf, Long> getRepository() {
        return shelfRepository;
    }

    @Override
    protected AbstractMapper<Shelf> getMapper() {
        return shelfMapper;
    }

    public Iterable<Shelf> getShelvesByUsername(String username) {
        return shelfRepository.findByUserUsername(username);
    }

    public Long create(Shelf domain, String username) {
        User user = userRepository.findUserByUsername(username);
        domain.setUser(user);
        return super.create(domain);
    }

    @Transactional
    public void update(Long id, Shelf newShelf, String username) throws NotFoundException, SyncException {
        Shelf shelf = shelfRepository.findByIdAndUserUsername(id, username)
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.UPDATE_NOT_FOUND));
        Shelf toUpdate = new Shelf();
        shelfMapper.update(shelf, toUpdate);
        shelfMapper.update(newShelf, toUpdate);

        toUpdate.setShelfBooks(shelf.getShelfBooks());
        shelfRepository.save(toUpdate);
    }

    public Shelf getShelf(Long id, String username) throws NotFoundException {
        return shelfRepository.findWithBooksByIdAndUserUsername(id, username)
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.GET_NOT_FOUND));
    }

    @Transactional
    public void addBookToShelf(Long id, Long bookId, String username) throws NotFoundException {
        Shelf shelf = shelfRepository.findWithBooksByIdAndUserUsername(id, username)
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.UPDATE_NOT_FOUND));
        ShelfBook shelfBook = shelfBookRepository.findById(new ShelfBook.ShelfBookId(id, bookId))
                .orElse(null);

        if (shelfBook == null) {
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new NotFoundException(ExceptionMessages.UPDATE_NOT_FOUND));
            shelfBookRepository.save(new ShelfBook(shelf, book, true));
        } else {
            shelfBook.setActive(true);
            shelfBookRepository.save(shelfBook);
        }
    }

    @Transactional
    public void removeBookFromShelf(Long id, Long bookId, String username) throws NotFoundException {
        Shelf shelf = shelfRepository.findWithBooksByIdAndUserUsername(id, username)
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.UPDATE_NOT_FOUND));
        ShelfBook shelfBook = shelfBookRepository.findById(new ShelfBook.ShelfBookId(id, bookId))
                .orElse(null);

        if (shelfBook == null) {
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new NotFoundException(ExceptionMessages.UPDATE_NOT_FOUND));
            shelfBookRepository.save(new ShelfBook(shelf, book, false));
        } else {
            shelfBook.setActive(false);
            shelfBookRepository.save(shelfBook);
        }
    }

    public void delete(Long id, String username) {
        shelfRepository.findByIdAndUserUsername(id, username)
                .ifPresent(shelf -> super.delete(id));
    }

}

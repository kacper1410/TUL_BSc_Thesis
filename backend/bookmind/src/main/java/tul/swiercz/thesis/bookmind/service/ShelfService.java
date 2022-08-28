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

import static tul.swiercz.thesis.bookmind.exception.ExceptionMessages.SYNC_ERROR;

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
    public void update(Long id, Shelf newShelf, String username) throws NotFoundException {
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

    private ShelfBook newShelfBook(Long bookId, Shelf shelf, boolean active) throws NotFoundException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.UPDATE_NOT_FOUND));
        return new ShelfBook(shelf, book, active);
    }

    @Transactional
    public void addBookToShelf(Long shelfId, Long bookId, String username) throws NotFoundException {
        Shelf shelf = shelfRepository.findWithBooksByIdAndUserUsername(shelfId, username)
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.UPDATE_NOT_FOUND));
        ShelfBook shelfBook = shelfBookRepository.findById(new ShelfBook.ShelfBookId(shelfId, bookId))
                .orElse(newShelfBook(bookId, shelf, true));

        shelfBook.setActive(true);
        shelfBookRepository.save(shelfBook);
    }

    @Transactional
    public void removeBookFromShelf(Long shelfId, Long bookId, String username) throws NotFoundException {
        Shelf shelf = shelfRepository.findWithBooksByIdAndUserUsername(shelfId, username)
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.UPDATE_NOT_FOUND));
        ShelfBook shelfBook = shelfBookRepository.findById(new ShelfBook.ShelfBookId(shelfId, bookId))
                .orElse(newShelfBook(bookId, shelf, false));

        shelfBook.setActive(false);
        shelfBookRepository.save(shelfBook);
    }

    @Transactional
    public void addBookToShelfSync(Long shelfId, Long bookId, String username, Long version) throws NotFoundException, SyncException {
        Shelf shelf = shelfRepository.findWithBooksByIdAndUserUsername(shelfId, username)
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.UPDATE_NOT_FOUND));
        ShelfBook shelfBook = shelfBookRepository.findById(new ShelfBook.ShelfBookId(shelfId, bookId))
                .orElse(null);

        if (version == null && shelfBook == null) {
            shelfBookRepository.save(newShelfBook(bookId, shelf, true));
            return;
        }

        if (version != null && shelfBook != null) {
            if (shelfBook.isActive()) {
                return;
            }

            if (version.equals(shelfBook.getVersion())) {
                shelfBook.setActive(true);
                shelfBookRepository.save(shelfBook);
                return;
            }
        }

        throw new SyncException(SYNC_ERROR);
    }

    @Transactional
    public void removeBookFromShelfSync(Long shelfId, Long bookId, String username, Long version) throws NotFoundException, SyncException {
        Shelf shelf = shelfRepository.findWithBooksByIdAndUserUsername(shelfId, username)
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.UPDATE_NOT_FOUND));
        ShelfBook shelfBook = shelfBookRepository.findById(new ShelfBook.ShelfBookId(shelfId, bookId))
                .orElse(null);

        if (version == null && shelfBook == null) {
            shelfBookRepository.save(newShelfBook(bookId, shelf, false));
            return;
        }

        if (version != null && shelfBook != null) {
            if (!shelfBook.isActive()) {
                return;
            }

            if (version.equals(shelfBook.getVersion())) {
                shelfBook.setActive(false);
                shelfBookRepository.save(shelfBook);
                return;
            }
        }

        throw new SyncException(SYNC_ERROR);
    }

    public void delete(Long id, String username) {
        shelfRepository.findByIdAndUserUsername(id, username)
                .ifPresent(shelf -> super.delete(id));
    }

}

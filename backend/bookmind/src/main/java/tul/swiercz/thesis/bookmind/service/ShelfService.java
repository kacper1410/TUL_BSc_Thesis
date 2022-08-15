package tul.swiercz.thesis.bookmind.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import tul.swiercz.thesis.bookmind.domain.*;
import tul.swiercz.thesis.bookmind.exception.ExceptionMessages;
import tul.swiercz.thesis.bookmind.exception.NotFoundException;
import tul.swiercz.thesis.bookmind.exception.SyncException;
import tul.swiercz.thesis.bookmind.mapper.AbstractMapper;
import tul.swiercz.thesis.bookmind.mapper.ShelfMapper;
import tul.swiercz.thesis.bookmind.repository.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class ShelfService extends CrudService<Shelf> {

    private final UserRepository userRepository;

    private final ShelfRepository shelfRepository;

    private final BookRepository bookRepository;

    private final ShelfMapper shelfMapper;

    private final ShelfActionRepository shelfActionRepository;

    private final ShelfBookRepository shelfBookRepository;

    @Autowired
    public ShelfService(UserRepository userRepository, ShelfRepository shelfRepository, BookRepository bookRepository, ShelfMapper shelfMapper, ShelfActionRepository shelfActionRepository, ShelfBookRepository shelfBookRepository) {
        this.userRepository = userRepository;
        this.shelfRepository = shelfRepository;
        this.bookRepository = bookRepository;
        this.shelfMapper = shelfMapper;
        this.shelfActionRepository = shelfActionRepository;
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
    public void update(Long id, Shelf newShelf, String username, LocalDateTime actionDate) throws NotFoundException, SyncException {
        Shelf shelf = shelfRepository.findByIdAndUserUsername(id, username)
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.UPDATE_NOT_FOUND));
        Shelf toUpdate = new Shelf();
        shelfMapper.update(shelf, toUpdate);
        shelfMapper.update(newShelf, toUpdate);

        ShelfAction dbAction = shelfActionRepository.findByShelfIdAndBookId(id, null);

        if (dbAction == null) {
            shelfRepository.save(toUpdate);

            ShelfAction performed = new ShelfAction(ShelfActionType.UPDATE, actionDate, toUpdate, null);
            shelfActionRepository.save(performed);
        } else if (actionDate.isAfter(dbAction.getActionDate())) {
            shelfRepository.save(toUpdate);

            dbAction.setActionDate(actionDate);
            shelfActionRepository.save(dbAction);
        } else {
            throw new SyncException(ExceptionMessages.SYNC_ERROR);
        }
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

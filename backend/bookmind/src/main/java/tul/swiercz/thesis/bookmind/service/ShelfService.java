package tul.swiercz.thesis.bookmind.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import tul.swiercz.thesis.bookmind.domain.Book;
import tul.swiercz.thesis.bookmind.domain.Shelf;
import tul.swiercz.thesis.bookmind.domain.User;
import tul.swiercz.thesis.bookmind.exception.ExceptionMessages;
import tul.swiercz.thesis.bookmind.exception.NotFoundException;
import tul.swiercz.thesis.bookmind.mapper.AbstractMapper;
import tul.swiercz.thesis.bookmind.mapper.ShelfMapper;
import tul.swiercz.thesis.bookmind.repository.BookRepository;
import tul.swiercz.thesis.bookmind.repository.ShelfRepository;
import tul.swiercz.thesis.bookmind.repository.UserRepository;

@Service
public class ShelfService extends CrudService<Shelf> {

    private final UserRepository userRepository;

    private final ShelfRepository shelfRepository;

    private final BookRepository bookRepository;

    private final ShelfMapper shelfMapper;

    @Autowired
    public ShelfService(UserRepository userRepository, ShelfRepository shelfRepository, BookRepository bookRepository, ShelfMapper shelfMapper) {
        this.userRepository = userRepository;
        this.shelfRepository = shelfRepository;
        this.bookRepository = bookRepository;
        this.shelfMapper = shelfMapper;
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

    public void update(Long id, Shelf newShelf, String username) throws NotFoundException {
        shelfRepository.findByIdAndUserUsername(id, username)
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.UPDATE_NOT_FOUND));
        super.update(id, newShelf);
    }

    public Shelf getShelf(Long id, String username) throws NotFoundException {
        return shelfRepository.findWithBooksByIdAndUserUsername(id, username)
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.GET_NOT_FOUND));
    }

    public void addBookToShelf(Long id, Long bookId, String username) throws NotFoundException {
        Shelf shelf = shelfRepository.findWithBooksByIdAndUserUsername(id, username)
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.UPDATE_NOT_FOUND));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.UPDATE_NOT_FOUND));
        if (shelf.getBooks().contains(book))
            return;
        shelf.getBooks().add(book);
        shelfRepository.save(shelf);
    }

    public void removeBookFromShelf(Long id, Long bookId, String username) throws NotFoundException {
        Shelf shelf = shelfRepository.findWithBooksByIdAndUserUsername(id, username)
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.UPDATE_NOT_FOUND));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.UPDATE_NOT_FOUND));
        shelf.getBooks().remove(book);
        shelfRepository.save(shelf);
    }

    public void delete(Long id, String username) {
        shelfRepository.findByIdAndUserUsername(id, username)
                .ifPresent(shelf -> super.delete(id));
    }

}

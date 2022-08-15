package tul.swiercz.thesis.bookmind.repository;

import org.springframework.data.repository.CrudRepository;
import tul.swiercz.thesis.bookmind.domain.Book;
import tul.swiercz.thesis.bookmind.domain.ShelfBook;

import java.util.Set;

public interface ShelfBookRepository extends CrudRepository<ShelfBook, ShelfBook.ShelfBookId> {

    Set<ShelfBook> findAllByBooksAndShelfUserUsername(Book book, String username);
}

package tul.swiercz.thesis.bookmind.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import tul.swiercz.thesis.bookmind.domain.Book;
import tul.swiercz.thesis.bookmind.domain.Shelf;

import java.util.Optional;
import java.util.Set;

public interface ShelfRepository extends CrudRepository<Shelf, Long> {

    Iterable<Shelf> findByUserUsername(String username);

    Optional<Shelf> findByIdAndUserUsername(Long id, String username);

    @EntityGraph(attributePaths = {"shelfBooks.books"})
    Optional<Shelf> findWithBooksByIdAndUserUsername(Long id, String username);

    Set<Shelf> findAllByShelfBooksBooksAndUserUsername(Book book, String username);
}

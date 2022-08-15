package tul.swiercz.thesis.bookmind.repository;

import org.springframework.data.repository.CrudRepository;
import tul.swiercz.thesis.bookmind.domain.ShelfBook;

public interface ShelfBookRepository extends CrudRepository<ShelfBook, ShelfBook.ShelfBookId> {

}

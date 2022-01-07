package tul.swiercz.thesis.bookmind.repository;

import org.springframework.data.repository.CrudRepository;
import tul.swiercz.thesis.bookmind.domain.Book;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findByTitle(String title);
}

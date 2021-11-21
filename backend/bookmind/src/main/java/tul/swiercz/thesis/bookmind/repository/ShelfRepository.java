package tul.swiercz.thesis.bookmind.repository;

import org.springframework.data.repository.CrudRepository;
import tul.swiercz.thesis.bookmind.domain.Shelf;

public interface ShelfRepository extends CrudRepository<Shelf, Long> {

    Iterable<Shelf> findByUserUsername(String username);

}

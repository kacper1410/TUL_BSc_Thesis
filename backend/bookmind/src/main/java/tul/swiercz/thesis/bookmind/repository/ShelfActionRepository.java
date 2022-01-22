package tul.swiercz.thesis.bookmind.repository;

import org.springframework.data.repository.CrudRepository;
import tul.swiercz.thesis.bookmind.domain.ShelfAction;

public interface ShelfActionRepository extends CrudRepository<ShelfAction, Long> {

    ShelfAction findByShelfIdAndBookId(Long shelfId, Long bookId);
}

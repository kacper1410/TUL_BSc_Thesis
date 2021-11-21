package tul.swiercz.thesis.bookmind.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import tul.swiercz.thesis.bookmind.domain.Shelf;
import tul.swiercz.thesis.bookmind.mapper.AbstractMapper;
import tul.swiercz.thesis.bookmind.mapper.ShelfMapper;
import tul.swiercz.thesis.bookmind.repository.ShelfRepository;

@Service
public class ShelfService extends CrudService<Shelf> {

    //region CrudService
    private final ShelfRepository shelfRepository;

    private ShelfMapper shelfMapper;

    @Autowired
    public ShelfService(ShelfRepository shelfRepository) {
        this.shelfRepository = shelfRepository;
    }

    @Override
    protected CrudRepository<Shelf, Long> getRepository() {
        return shelfRepository;
    }

    @Override
    protected AbstractMapper<Shelf> getMapper() {
        return shelfMapper;
    }
    //endregion

    public Iterable<Shelf> getShelvesByUsername(String username) {
        return shelfRepository.findByUserUsername(username);
    }

}

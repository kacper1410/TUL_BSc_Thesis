package tul.swiercz.thesis.bookmind.service;

import org.springframework.data.repository.CrudRepository;
import tul.swiercz.thesis.bookmind.domain.AbstractDomain;
import tul.swiercz.thesis.bookmind.mapper.AbstractMapper;

import java.util.NoSuchElementException;

public abstract class CRUDService<DOMAIN extends AbstractDomain> {

    protected abstract CrudRepository<DOMAIN, Long> getRepository();

    protected abstract AbstractMapper<DOMAIN> getMapper();

    public Iterable<DOMAIN> getAll() {
        return getRepository().findAll();
    }

    public DOMAIN getById(Long id) {
        return getRepository().findById(id).orElseThrow();
    }

    public Long create(DOMAIN domain) {
        return getRepository().save(domain).getId();
    }

    public void update(Long id, DOMAIN domain) {
            DOMAIN toUpdate = getRepository()
                    .findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Object to update not found"));
            getMapper().update(domain, toUpdate);
            getRepository().save(toUpdate);
    }

    public void delete(Long id) {
        getRepository().deleteById(id);
    }

}

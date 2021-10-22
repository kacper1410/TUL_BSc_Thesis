package tul.swiercz.thesis.bookmind.service;

import org.springframework.data.repository.CrudRepository;
import tul.swiercz.thesis.bookmind.domain.AbstractDomain;

import java.util.NoSuchElementException;

public abstract class CRUDService<DOMAIN extends AbstractDomain> {

    protected abstract CrudRepository<DOMAIN, Long> getRepository();

    public Iterable<DOMAIN> getAll() {
        return getRepository().findAll();
    }

    public DOMAIN getById(Long id) {
        return getRepository().findById(id).orElseThrow();
    }

    public Long create(DOMAIN domain) {
        return getRepository().save(domain).getId();
    }

    public Long update(DOMAIN domain) {
        if (getRepository().existsById(domain.getId())) {
            return getRepository().save(domain).getId();
        } else {
            throw new NoSuchElementException("Object to update not found");
        }
    }

    public void delete(Long id) {
        getRepository().deleteById(id);
    }

}

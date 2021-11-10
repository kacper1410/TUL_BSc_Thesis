package tul.swiercz.thesis.bookmind.service;

import org.springframework.data.repository.CrudRepository;
import tul.swiercz.thesis.bookmind.domain.AbstractDomain;
import tul.swiercz.thesis.bookmind.exception.ExceptionMessages;
import tul.swiercz.thesis.bookmind.exception.NotFoundException;
import tul.swiercz.thesis.bookmind.mapper.AbstractMapper;

public abstract class CrudService<DOMAIN extends AbstractDomain> {

    protected abstract CrudRepository<DOMAIN, Long> getRepository();

    protected abstract AbstractMapper<DOMAIN> getMapper();

    public Iterable<DOMAIN> getAll() {
        return getRepository().findAll();
    }

    public DOMAIN getById(Long id) throws NotFoundException {
        return getRepository().findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.GET_NOT_FOUND));
    }

    public Long create(DOMAIN domain) {
        return getRepository().save(domain).getId();
    }

    public void update(Long id, DOMAIN newDomain) throws NotFoundException {
            DOMAIN toUpdate = getRepository().findById(id)
                    .orElseThrow(() -> new NotFoundException(ExceptionMessages.UPDATE_NOT_FOUND));
            getMapper().update(newDomain, toUpdate);
            getRepository().save(toUpdate);
    }

    public void delete(Long id) {
        getRepository().deleteById(id);
    }

}

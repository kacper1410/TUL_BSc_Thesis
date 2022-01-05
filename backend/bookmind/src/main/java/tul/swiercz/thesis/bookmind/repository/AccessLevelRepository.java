package tul.swiercz.thesis.bookmind.repository;

import org.springframework.data.repository.CrudRepository;
import tul.swiercz.thesis.bookmind.domain.AccessLevel;

import java.util.Optional;

public interface AccessLevelRepository extends CrudRepository<AccessLevel, Long> {

    Optional<AccessLevel> findByAuthority(String authority);

}

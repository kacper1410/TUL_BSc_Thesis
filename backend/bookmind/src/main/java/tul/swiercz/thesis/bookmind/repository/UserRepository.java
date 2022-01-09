package tul.swiercz.thesis.bookmind.repository;

import org.springframework.data.repository.CrudRepository;
import tul.swiercz.thesis.bookmind.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findUserByUsername(String username);
}

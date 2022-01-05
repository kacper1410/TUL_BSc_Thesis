package tul.swiercz.thesis.bookmind.mapper;

import org.mapstruct.Mapper;
import tul.swiercz.thesis.bookmind.domain.User;
import tul.swiercz.thesis.bookmind.dto.user.CreateUser;

@Mapper(componentModel = "spring")
public interface UserMapper extends AbstractMapper<User> {

    User createToUser(CreateUser createUser);
}

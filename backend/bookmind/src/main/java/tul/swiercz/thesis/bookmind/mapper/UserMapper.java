package tul.swiercz.thesis.bookmind.mapper;

import org.mapstruct.Mapper;
import tul.swiercz.thesis.bookmind.domain.User;
import tul.swiercz.thesis.bookmind.dto.user.CreateUser;
import tul.swiercz.thesis.bookmind.dto.user.UserInfo;
import tul.swiercz.thesis.bookmind.dto.user.UserListInfo;

@Mapper(componentModel = "spring")
public interface UserMapper extends AbstractMapper<User> {

    User createToUser(CreateUser createUser);

    Iterable<UserListInfo> usersToDtos(Iterable<User> users);

    UserInfo userToDto(User user);
}

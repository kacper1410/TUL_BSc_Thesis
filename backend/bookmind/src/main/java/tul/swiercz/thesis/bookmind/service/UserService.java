package tul.swiercz.thesis.bookmind.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tul.swiercz.thesis.bookmind.domain.AccessLevel;
import tul.swiercz.thesis.bookmind.domain.User;
import tul.swiercz.thesis.bookmind.exception.ExceptionMessages;
import tul.swiercz.thesis.bookmind.exception.InternalException;
import tul.swiercz.thesis.bookmind.mapper.AbstractMapper;
import tul.swiercz.thesis.bookmind.mapper.UserMapper;
import tul.swiercz.thesis.bookmind.repository.AccessLevelRepository;
import tul.swiercz.thesis.bookmind.repository.UserRepository;
import tul.swiercz.thesis.bookmind.security.Roles;

import java.util.List;

@Service
public class UserService extends CrudService<User> implements UserDetailsService {

    private final UserRepository userRepository;

    private final AccessLevelRepository accessLevelRepository;

    private final UserMapper userMapper;

    private final MailService mailService;

    @Autowired
    public UserService(UserRepository userRepository, AccessLevelRepository accessLevelRepository, UserMapper userMapper, MailService mailService) {
        this.userRepository = userRepository;
        this.accessLevelRepository = accessLevelRepository;
        this.userMapper = userMapper;
        this.mailService = mailService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username);
    }

    @Override
    protected CrudRepository<User, Long> getRepository() {
        return userRepository;
    }

    @Override
    protected AbstractMapper<User> getMapper() {
        return userMapper;
    }

    public Long register(User user) throws InternalException {
        setReaderAuthority(user);
        mailService.sendRegisterConfirmation(user.getEmail());
        return super.create(user);
    }

    private void setReaderAuthority(User user) throws InternalException {
        AccessLevel accessLevel = accessLevelRepository.findByAuthority("ROLE_" + Roles.READER)
                .orElseThrow(() -> new InternalException(ExceptionMessages.INTERNAL_EXCEPTION));
        user.setAuthorities(List.of(accessLevel));
    }
}

package tul.swiercz.thesis.bookmind.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tul.swiercz.thesis.bookmind.domain.AccessLevel;
import tul.swiercz.thesis.bookmind.domain.OneTimeCode;
import tul.swiercz.thesis.bookmind.domain.User;
import tul.swiercz.thesis.bookmind.exception.ExceptionMessages;
import tul.swiercz.thesis.bookmind.exception.InternalException;
import tul.swiercz.thesis.bookmind.exception.NotFoundException;
import tul.swiercz.thesis.bookmind.mapper.AbstractMapper;
import tul.swiercz.thesis.bookmind.mapper.UserMapper;
import tul.swiercz.thesis.bookmind.repository.AccessLevelRepository;
import tul.swiercz.thesis.bookmind.repository.UserRepository;
import tul.swiercz.thesis.bookmind.security.Roles;

import java.util.Set;

@Service
public class UserService extends CrudService<User> implements UserDetailsService {

    private final UserRepository userRepository;

    private final AccessLevelRepository accessLevelRepository;

    private final UserMapper userMapper;

    private final MailService mailService;

    private final OneTimeCodeService oneTimeCodeService;

    @Autowired
    public UserService(UserRepository userRepository, AccessLevelRepository accessLevelRepository,
                       UserMapper userMapper, MailService mailService, OneTimeCodeService oneTimeCodeService) {
        this.userRepository = userRepository;
        this.accessLevelRepository = accessLevelRepository;
        this.userMapper = userMapper;
        this.mailService = mailService;
        this.oneTimeCodeService = oneTimeCodeService;
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
        encryptPassword(user);
        Long id = super.create(user);
        OneTimeCode oneTimeCode = oneTimeCodeService.generateRegistrationCode(user);
        mailService.sendRegisterConfirmation(user.getEmail(), oneTimeCode.getCode());
        return id;
    }

    private void encryptPassword(User user) {
        String encoded = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encoded);
    }

    private void setReaderAuthority(User user) throws InternalException {
        AccessLevel accessLevel = accessLevelRepository.findByAuthority("ROLE_" + Roles.READER)
                .orElseThrow(() -> new InternalException(ExceptionMessages.INTERNAL_EXCEPTION));
        user.setAuthorities(Set.of(accessLevel));
    }

    public void confirmUser(String code) throws NotFoundException {
        User user = oneTimeCodeService.getUserToActivate(code);
        user.setEnabled(true);
        userRepository.save(user);
    }

    public User getByUsername(String name) {
        return userRepository.findUserByUsername(name);
    }

    public void addAccessLevel(AccessLevel toAdd, Long userId) throws NotFoundException, InternalException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.UPDATE_NOT_FOUND));
        AccessLevel accessLevel = accessLevelRepository.findByAuthority(toAdd.getAuthority())
                .orElseThrow(() -> new InternalException(ExceptionMessages.INTERNAL_EXCEPTION));
        user.getAuthorities().add(accessLevel);
        userRepository.save(user);
    }

    public void removeAccessLevel(AccessLevel toRemove, Long userId) throws NotFoundException, InternalException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.UPDATE_NOT_FOUND));
        AccessLevel accessLevel = accessLevelRepository.findByAuthority(toRemove.getAuthority())
                .orElseThrow(() -> new InternalException(ExceptionMessages.INTERNAL_EXCEPTION));
        user.getAuthorities().remove(accessLevel);
        userRepository.save(user);
    }
}

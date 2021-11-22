package tul.swiercz.thesis.bookmind.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tul.swiercz.thesis.bookmind.domain.Shelf;
import tul.swiercz.thesis.bookmind.domain.User;
import tul.swiercz.thesis.bookmind.repository.ShelfRepository;
import tul.swiercz.thesis.bookmind.repository.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ShelfServiceTest {

    @InjectMocks
    private ShelfService shelfService;

    @Mock
    private ShelfRepository shelfRepository;

    @Mock
    private UserRepository userRepository;

    private Iterable<Shelf> shelves;

    private Shelf shelf1;

    private Shelf shelf2;

    private String username;

    private User user;

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void initFields() {
        username = "username";
        shelf1 = new Shelf("name1");
        shelf2 = new Shelf("name2");
        user = new User();
        user.setUsername(username);

        shelves = List.of(shelf1, shelf2);
    }

    @Test
    void getShelvesByUsername() {
        when(shelfRepository.findByUserUsername(username)).thenReturn(shelves);

        Iterable<Shelf> shelfIterable = shelfService.getShelvesByUsername(username);

        assertEquals(shelves, shelfIterable);
    }

    @Test
    void create() {
        when(shelfRepository.save(shelf1)).thenReturn(shelf1);
        when(userRepository.findUserByUsername(username)).thenReturn(user);

        shelfService.create(shelf1, username);

        verify(shelfRepository).save(shelf1);
        assertEquals(user, shelf1.getUser());
    }

}

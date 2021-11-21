package tul.swiercz.thesis.bookmind.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tul.swiercz.thesis.bookmind.domain.Shelf;
import tul.swiercz.thesis.bookmind.repository.ShelfRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ShelfServiceTest {

    @InjectMocks
    private ShelfService shelfService;

    @Mock
    private ShelfRepository shelfRepository;

    private Iterable<Shelf> shelves;

    private Shelf shelf1;

    private Shelf shelf2;

    private String username;

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void initFields() {
        username = "username";
        shelf1 = new Shelf("name1");
        shelf2 = new Shelf("name2");

        shelves = List.of(shelf1, shelf2);
    }

    @Test
    void getShelvesByUsername() {
        when(shelfRepository.findByUserUsername(username)).thenReturn(shelves);

        Iterable<Shelf> shelfIterable = shelfService.getShelvesByUsername(username);

        assertEquals(shelves, shelfIterable);
    }

}

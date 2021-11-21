package tul.swiercz.thesis.bookmind.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tul.swiercz.thesis.bookmind.domain.Shelf;
import tul.swiercz.thesis.bookmind.dto.shelf.ShelfListInfo;
import tul.swiercz.thesis.bookmind.mapper.ShelfMapper;
import tul.swiercz.thesis.bookmind.service.ShelfService;

import java.security.Principal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ShelfControllerTest {

    @InjectMocks
    private ShelfController shelfController;

    @Mock
    private ShelfService shelfService;

    @Mock
    private ShelfMapper shelfMapper;

    @Mock
    private Principal principal;

    private List<Shelf> shelves;

    private List<ShelfListInfo> shelfListInfos;

    private Shelf shelf;

    private Shelf shelf2;

    private ShelfListInfo shelfListInfo;

    private ShelfListInfo shelfListInfo2;

    private String username;

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void initFields() {
        username = "username";
        shelf = new Shelf("name1");
        shelf2 = new Shelf("name2");
        shelfListInfo = new ShelfListInfo("name1ListInfo");
        shelfListInfo2 = new ShelfListInfo("name1ListInfo");

        shelves = List.of(shelf, shelf2);
        shelfListInfos = List.of(shelfListInfo, shelfListInfo2);
    }

    @Test
    void getMyShelves() {
        when(principal.getName()).thenReturn(username);
        when(shelfService.getShelvesByUsername(username)).thenReturn(shelves);
        when(shelfMapper.shelfToListInfo(shelves)).thenReturn(shelfListInfos);

        ResponseEntity<Iterable<ShelfListInfo>> response = shelfController.getMyShelves(principal);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(shelfListInfos, response.getBody());
    }
}

package tul.swiercz.thesis.bookmind.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tul.swiercz.thesis.bookmind.domain.Shelf;
import tul.swiercz.thesis.bookmind.dto.shelf.CreateShelf;
import tul.swiercz.thesis.bookmind.dto.shelf.ModifyShelf;
import tul.swiercz.thesis.bookmind.dto.shelf.ShelfInfo;
import tul.swiercz.thesis.bookmind.dto.shelf.ShelfListInfo;
import tul.swiercz.thesis.bookmind.exception.NotFoundException;
import tul.swiercz.thesis.bookmind.exception.SyncException;
import tul.swiercz.thesis.bookmind.mapper.ShelfMapper;
import tul.swiercz.thesis.bookmind.service.ShelfService;

import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
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

    private ShelfInfo shelfInfo;

    private ShelfListInfo shelfListInfo2;

    private CreateShelf createShelf;

    private ModifyShelf modifyShelf;

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
        shelfInfo = new ShelfInfo("name1Info", new ArrayList<>());
        shelfListInfo2 = new ShelfListInfo("name1ListInfo");
        createShelf = new CreateShelf("create");
        modifyShelf = new ModifyShelf("modify");

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

    @Test
    void addShelf() {
        when(shelfMapper.createToShelf(createShelf)).thenReturn(shelf);
        when(shelfService.create(shelf, username)).thenReturn(1L);
        when(principal.getName()).thenReturn(username);

        ResponseEntity<?> response = shelfController.add(createShelf, principal);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNull(response.getBody());
        assertEquals(URI.create("/shelves/me/1"), response.getHeaders().getLocation());
    }

    @Test
    void update() throws NotFoundException, SyncException {
        when(shelfMapper.modifyToShelf(modifyShelf)).thenReturn(shelf);
        when(principal.getName()).thenReturn(username);

        ResponseEntity<?> response = shelfController.update(1L, modifyShelf, principal);

        verify(shelfService).update(eq(1L), eq(shelf), eq(username));
        assertNull(response.getBody());
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    @Test
    void getShelfDetails() throws NotFoundException {
        when(principal.getName()).thenReturn(username);
        when(shelfService.getShelf(1L, username)).thenReturn(shelf);
        when(shelfMapper.shelfToInfo(shelf)).thenReturn(shelfInfo);

        ResponseEntity<?> response = shelfController.getShelfDetails(1L, principal);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(shelfInfo, response.getBody());
    }

    @Test
    void addBookToShelf() throws NotFoundException {
        when(principal.getName()).thenReturn(username);

        ResponseEntity<?> response = shelfController.addBookToShelf(1L, 2L, principal);

        verify(shelfService).addBookToShelf(eq(1L), eq(2L), eq(username));
        assertNull(response.getBody());
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    @Test
    void removeBookFromShelf() throws NotFoundException {
        when(principal.getName()).thenReturn(username);

        ResponseEntity<?> response = shelfController.removeBookFromShelf(1L, 2L, principal);

        verify(shelfService).removeBookFromShelf(eq(1L), eq(2L), eq(username));
        assertNull(response.getBody());
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    @Test
    void delete() {
        when(principal.getName()).thenReturn(username);

        ResponseEntity<?> response = shelfController.delete(1L, principal);

        verify(shelfService).delete(1L, username);
        assertNull(response.getBody());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

}

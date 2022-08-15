package tul.swiercz.thesis.bookmind.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tul.swiercz.thesis.bookmind.domain.Shelf;
import tul.swiercz.thesis.bookmind.domain.ShelfActionType;
import tul.swiercz.thesis.bookmind.dto.action.ShelfActionBookDto;
import tul.swiercz.thesis.bookmind.dto.action.ShelfActionDto;
import tul.swiercz.thesis.bookmind.dto.action.ShelfActionModifyDto;
import tul.swiercz.thesis.bookmind.dto.shelf.ModifyShelf;
import tul.swiercz.thesis.bookmind.exception.NotFoundException;
import tul.swiercz.thesis.bookmind.exception.SyncException;
import tul.swiercz.thesis.bookmind.mapper.ShelfMapper;
import tul.swiercz.thesis.bookmind.service.ShelfService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class SyncControllerTest {

    @InjectMocks
    private SyncController syncController;

    @Mock
    private ShelfService shelfService;

    @Mock
    private ShelfMapper shelfMapper;

    @Mock
    private Principal principal;

    private ShelfActionBookDto action1;
    private ShelfActionBookDto action2;
    private ShelfActionModifyDto action3;
    private ModifyShelf modifyShelf;
    private Shelf shelf;
    private List<ShelfActionDto> actions;

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void initFields() {
        action1 = new ShelfActionBookDto();
        action1.setShelfActionType(ShelfActionType.ADD_BOOK);
        action1.setActionDate(LocalDateTime.now());
        action1.setBookId(3L);

        action2 = new ShelfActionBookDto();
        action2.setShelfActionType(ShelfActionType.REMOVE_BOOK);
        action2.setActionDate(LocalDateTime.now());
        action2.setBookId(4L);

        modifyShelf = new ModifyShelf("shelfName");
        action3 = new ShelfActionModifyDto();
        action3.setShelfActionType(ShelfActionType.UPDATE);
        action3.setActionDate(LocalDateTime.now());
        action3.setShelf(modifyShelf);

        shelf = new Shelf("name");

        actions = new ArrayList<>();
    }

    @Test
    void sync() throws NotFoundException, SyncException {
        when(principal.getName()).thenReturn("username");
        when(shelfMapper.modifyToShelf(modifyShelf)).thenReturn(shelf);
        actions.add(action1);
        actions.add(action2);
        actions.add(action3);

        ResponseEntity<?> response = syncController.sync(2L, actions, principal);

        verify(shelfService).update(eq(2L), eq(shelf), eq("username"));
        verify(shelfService).addBookToShelf(eq(2L), eq(action1.getBookId()), eq("username"));
        verify(shelfService).removeBookFromShelf(eq(2L), eq(action2.getBookId()), eq("username"));
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void syncException() throws NotFoundException, SyncException {
        when(principal.getName()).thenReturn("username");
        when(shelfMapper.modifyToShelf(modifyShelf)).thenReturn(shelf);
        actions.add(action1);
        actions.add(action2);
        actions.add(action3);
        doThrow(SyncException.class).when(shelfService)
                .addBookToShelf(2L, action1.getBookId(), "username");

        ResponseEntity<?> response = syncController.sync(2L, actions, principal);

        verify(shelfService).update(eq(2L), eq(shelf), eq("username"));
        verify(shelfService).addBookToShelf(eq(2L), eq(action1.getBookId()), eq("username"));
        verify(shelfService).removeBookFromShelf(eq(2L), eq(action2.getBookId()), eq("username"));
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(List.of(action1), response.getBody());
    }
}

package tul.swiercz.thesis.bookmind.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import tul.swiercz.thesis.bookmind.dto.action.ShelfActionBookDto;
import tul.swiercz.thesis.bookmind.dto.action.ShelfActionDto;
import tul.swiercz.thesis.bookmind.dto.action.ShelfActionModifyDto;
import tul.swiercz.thesis.bookmind.exception.NotFoundException;
import tul.swiercz.thesis.bookmind.exception.SyncException;
import tul.swiercz.thesis.bookmind.mapper.ShelfMapper;
import tul.swiercz.thesis.bookmind.security.Roles;
import tul.swiercz.thesis.bookmind.service.ShelfService;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@DenyAll
@RequestMapping("/sync")
public class SyncController {

    private final ShelfService shelfService;

    private final ShelfMapper shelfMapper;

    @Autowired
    public SyncController(ShelfService shelfService, ShelfMapper shelfMapper) {
        this.shelfService = shelfService;
        this.shelfMapper = shelfMapper;
    }

    @PostMapping("/{shelfId}")
    @RolesAllowed(Roles.READER)
    public ResponseEntity<?> sync(@PathVariable Long shelfId, @RequestBody List<ShelfActionDto> shelfActionDtos, Principal principal) throws NotFoundException {
        List<ShelfActionDto> failed = new ArrayList<>();
        for (ShelfActionDto action : shelfActionDtos) {
            try {
                performAction(shelfId, action, principal.getName());
            } catch (SyncException e) {
                failed.add(action);
            }
        }
        return failed.isEmpty()? ResponseEntity.accepted().build() : ResponseEntity.status(HttpStatus.CONFLICT).body(failed);
    }

    private void performAction(Long shelfId, ShelfActionDto action, String username) throws NotFoundException, SyncException {
        switch (action.getShelfActionType()) {
            case UPDATE:
                shelfService.update(shelfId, shelfMapper.modifyToShelf(((ShelfActionModifyDto) action).getShelf()), username);
                break;
            case ADD_BOOK:
                shelfService.addBookToShelf(shelfId, ((ShelfActionBookDto) action).getBookId(), username);
                break;
            case REMOVE_BOOK:
                shelfService.removeBookFromShelf(shelfId, ((ShelfActionBookDto) action).getBookId(), username);
        }
    }

}

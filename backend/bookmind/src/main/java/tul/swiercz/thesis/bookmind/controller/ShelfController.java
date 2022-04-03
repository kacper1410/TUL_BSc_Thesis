package tul.swiercz.thesis.bookmind.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tul.swiercz.thesis.bookmind.domain.Shelf;
import tul.swiercz.thesis.bookmind.dto.shelf.CreateShelf;
import tul.swiercz.thesis.bookmind.dto.shelf.ModifyShelf;
import tul.swiercz.thesis.bookmind.dto.shelf.ShelfInfo;
import tul.swiercz.thesis.bookmind.dto.shelf.ShelfListInfo;
import tul.swiercz.thesis.bookmind.exception.NotFoundException;
import tul.swiercz.thesis.bookmind.exception.SyncException;
import tul.swiercz.thesis.bookmind.mapper.ShelfMapper;
import tul.swiercz.thesis.bookmind.security.Roles;
import tul.swiercz.thesis.bookmind.service.ShelfService;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@DenyAll
@RequestMapping("/shelves")
public class ShelfController {

    private final ShelfService shelfService;

    private final ShelfMapper shelfMapper;

    @Autowired
    public ShelfController(ShelfService shelfService, ShelfMapper shelfMapper) {
        this.shelfService = shelfService;
        this.shelfMapper = shelfMapper;
    }

    @GetMapping("/me")
    @RolesAllowed(Roles.READER)
    public ResponseEntity<Iterable<ShelfListInfo>> getMyShelves(Principal principal) {
        Iterable<Shelf> shelves = shelfService.getShelvesByUsername(principal.getName());
        return ResponseEntity.ok(shelfMapper.shelfToListInfo(shelves));
    }

    @PostMapping("/me")
    @RolesAllowed(Roles.READER)
    public ResponseEntity<?> add(@RequestBody CreateShelf createShelf, Principal principal) {
        Long id = shelfService.create(shelfMapper.createToShelf(createShelf), principal.getName());
        return ResponseEntity.created(URI.create("/shelves/me/" + id)).build();
    }

    @PutMapping("/me/{id}")
    @RolesAllowed(Roles.READER)
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid ModifyShelf modifyShelf, Principal principal) throws NotFoundException, SyncException {
        shelfService.update(id, shelfMapper.modifyToShelf(modifyShelf), principal.getName(), LocalDateTime.now());
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/me/{id}")
    @RolesAllowed(Roles.READER)
    public ResponseEntity<ShelfInfo> getShelfDetails(@PathVariable Long id, Principal principal) throws NotFoundException {
        Shelf shelf = shelfService.getShelf(id, principal.getName());
        return ResponseEntity.ok(shelfMapper.shelfToInfo(shelf));
    }

    @PutMapping("/me/{id}/book/{bookId}")
    @RolesAllowed(Roles.READER)
    public ResponseEntity<?> addBookToShelf(@PathVariable Long id, @PathVariable Long bookId, Principal principal) throws NotFoundException, SyncException {
        shelfService.addBookToShelf(id, bookId, principal.getName(), LocalDateTime.now());
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/me/{id}/book/{bookId}")
    @RolesAllowed(Roles.READER)
    public ResponseEntity<?> removeBookFromShelf(@PathVariable Long id, @PathVariable Long bookId, Principal principal) throws NotFoundException, SyncException {
        shelfService.removeBookFromShelf(id, bookId, principal.getName(), LocalDateTime.now());
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/me/{id}")
    @RolesAllowed(Roles.READER)
    public ResponseEntity<?> delete(@PathVariable Long id, Principal principal) {
        shelfService.delete(id, principal.getName());
        return ResponseEntity.noContent().build();
    }
}

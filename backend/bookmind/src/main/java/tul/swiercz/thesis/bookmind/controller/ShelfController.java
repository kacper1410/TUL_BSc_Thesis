package tul.swiercz.thesis.bookmind.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import tul.swiercz.thesis.bookmind.domain.Shelf;
import tul.swiercz.thesis.bookmind.dto.shelf.CreateShelf;
import tul.swiercz.thesis.bookmind.dto.shelf.ShelfListInfo;
import tul.swiercz.thesis.bookmind.mapper.ShelfMapper;
import tul.swiercz.thesis.bookmind.security.Roles;
import tul.swiercz.thesis.bookmind.service.ShelfService;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import java.net.URI;
import java.security.Principal;

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
    public ResponseEntity<?> addShelf(@RequestBody CreateShelf createShelf, Principal principal) {
        Long id = shelfService.create(shelfMapper.createToShelf(createShelf), principal.getName());
        return ResponseEntity.created(URI.create("/shelves/me/" + id)).build();
    }

}

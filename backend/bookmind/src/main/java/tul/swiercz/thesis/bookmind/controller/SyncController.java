package tul.swiercz.thesis.bookmind.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import tul.swiercz.thesis.bookmind.domain.ShelfAction;
import tul.swiercz.thesis.bookmind.dto.action.ShelfActionDto;
import tul.swiercz.thesis.bookmind.mapper.ShelfActionMapper;
import tul.swiercz.thesis.bookmind.security.Roles;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import java.security.Principal;

@Controller
@DenyAll
@RequestMapping("/sync")
public class SyncController {

    private final ShelfActionMapper shelfActionMapper;

    @Autowired
    public SyncController(ShelfActionMapper shelfActionMapper) {
        this.shelfActionMapper = shelfActionMapper;
    }

    @PostMapping("{shelfId}")
    @RolesAllowed(Roles.READER)
    public ResponseEntity<?> sync(@PathVariable String shelfId, @RequestBody ShelfActionDto shelfActionDto, Principal principal) {
        ShelfAction action = shelfActionMapper.dtoToShelfAction(shelfActionDto);

        return ResponseEntity.accepted().build();
    }

}

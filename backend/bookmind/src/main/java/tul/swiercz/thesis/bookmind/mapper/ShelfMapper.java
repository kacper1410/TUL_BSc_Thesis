package tul.swiercz.thesis.bookmind.mapper;

import org.mapstruct.Mapper;
import tul.swiercz.thesis.bookmind.domain.Shelf;
import tul.swiercz.thesis.bookmind.dto.shelf.CreateShelf;
import tul.swiercz.thesis.bookmind.dto.shelf.ModifyShelf;
import tul.swiercz.thesis.bookmind.dto.shelf.ShelfInfo;
import tul.swiercz.thesis.bookmind.dto.shelf.ShelfListInfo;

@Mapper(componentModel = "spring")
public interface ShelfMapper extends AbstractMapper<Shelf> {

    Iterable<ShelfListInfo> shelfToListInfo(Iterable<Shelf> shelf);

    Shelf createToShelf(CreateShelf createShelf);

    Shelf modifyToShelf(ModifyShelf modifyShelf);

    ShelfInfo shelfToInfo(Shelf shelf);
}

package tul.swiercz.thesis.bookmind.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import tul.swiercz.thesis.bookmind.domain.ShelfBook;
import tul.swiercz.thesis.bookmind.dto.book.BookOnShelfListInfo;
import tul.swiercz.thesis.bookmind.dto.shelf.ShelfForBookListInfo;

@Mapper(componentModel = "spring")
public interface ShelfBookMapper {

    @Mappings({
            @Mapping(source = "version", target = "connectionVersion"),
            @Mapping(source = "book.title", target = "title"),
            @Mapping(source = "book.author", target = "author"),
            @Mapping(source = "book.id", target = "id"),
            @Mapping(source = "book.version", target = "version")
    })
    BookOnShelfListInfo mapToBookOnShelf(ShelfBook shelfBook);

    @Mappings({
            @Mapping(source = "version", target = "connectionVersion"),
            @Mapping(source = "shelf.name", target = "name"),
            @Mapping(source = "shelf.id", target = "id"),
            @Mapping(source = "shelf.version", target = "version")
    })
    ShelfForBookListInfo mapToShelfForBook(ShelfBook shelfBook);

    Iterable<ShelfForBookListInfo> mapToShelvesForBook(Iterable<ShelfBook> books);
}
